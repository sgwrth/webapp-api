package de.asiegwarth.jwtaws.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.asiegwarth.jwtaws.entity.UploadData;
import de.asiegwarth.jwtaws.entity.UploadResponse;
import de.asiegwarth.jwtaws.upload.UploadException;
import de.asiegwarth.jwtaws.upload.UploadProperties;


public interface UploadService {

    @Service
    public class UploadServiceImpl implements UploadService {

        // private final DocumentService documentService;
        private final UserService userService;
        private final SystemVarsService systemVarsServ;
        private final Path baseDir;

        @Autowired
        DocumentService documentService;

        @Autowired
        public UploadServiceImpl(
                // DocumentService documentService,
                UserService userService,
                SystemVarsService systemVarsService,
                UploadProperties properties
        ) {
            // this.documentService = documentService;
            this.userService = userService;
            this.baseDir = setBaseDir(properties);
            this.systemVarsServ = systemVarsService;

        }
        
        @Override
        public Path setBaseDir(UploadProperties properties) {
            if (0 == properties.getBaseDir().trim().length()) {
                throw new UploadException(
                    "Path name for file upload location cannot be empty."
                ); 
            } else {
                return Paths.get(properties.getBaseDir());
            }
        }

        @Override
        public void init() {
            try {
                Files.createDirectories(this.baseDir);
            }
            catch (IOException e) {
                throw new UploadException("Could not initialize upload", e);
            }
        }
      
        @Override
        public ResponseEntity<UploadResponse> store(
                RedirectAttributes redirectAttributes,
                UploadData uploadData
        ) {
            MultipartFile file = uploadData.getFile();
            try {
                if (file.isEmpty()) {
                    throw new UploadException("Failed to store empty file.");
                }
                if (systemVarsServ.getMaxUploadSize() < file.getSize()) {
                    System.out.println("File size: "
                            + uploadData.getFile().getSize() / (1024 * 1024));
                    throw new UploadException("File exceeds max. size."); 
                }
                if (isFiletypeValid(file)) {
                    String fullPath = getFullPath();
                    String fileName = getUuidFilename(
                            fullPath,
                            getFileExtension(file)
                    );
                    Path destinationFile =
                            Paths.get(fullPath, "/", fileName).toAbsolutePath();
                    checkUploadPathInsideCurrentDir(destinationFile);
                    InputStream inputStream =
                            file.getInputStream();
                    Long bytesWritten =
                            copyFile(fullPath, inputStream, destinationFile);
                    int doctype = getDoctype(file);
                    createDbEntry(bytesWritten, uploadData, fileName, doctype);
                    return ResponseEntity.ok(new UploadResponse(
                            fullPath,
                            fileName
                    ));
                } else {
                    return ResponseEntity.badRequest()
                            .body(new UploadResponse(null, null));
                }
            }
            catch (IOException e) {
                throw new UploadException("Failed to store file.", e);
            }
        }

        @Override
        public boolean isFiletypeValid(MultipartFile file) {
            switch (file.getContentType()) {
                case "image/png":
                    return true;
                case "application/pdf":
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public String getFullPath() {
            LocalDate year = LocalDate.now();
            int kw = year.get(WeekFields.of(Locale.GERMANY).weekOfYear());
            String kwString =
                    (10 > kw) ? "0" + String.valueOf(kw) : String.valueOf(kw);
            DateTimeFormatter yyyy = DateTimeFormatter.ofPattern("yyyy");
            return this.baseDir + "/" + year.format(yyyy) + "/" + kwString; 
        }

        @Override
        public String getFullPath(int year, int kw) {
            String kwString =
                    (10 > kw) ? "0" + String.valueOf(kw) : String.valueOf(kw);
            return this.baseDir + "/" + String.valueOf(year) + "/" + kwString; 
        }

        @Override
        public void checkUploadPathInsideCurrentDir(Path destinationFile) {
            Path fullDir = Paths.get(getFullPath());
            if (!destinationFile.getParent().equals(fullDir.toAbsolutePath())) {
                throw new UploadException(
                    "Cannot store file outside current directory."
                );
            }
        }

        @Override
        public int getDoctype(MultipartFile file) {
            if (file.getContentType().equals("image/png")) {
                return 1;
            } 
            if (file.getContentType().equals("application/pdf")) {
                return 2;
            }
            return 0;
        }

        @Override
        public void createDbEntry(
                Long bytesWritten,
                UploadData uploadData,
                String fileName,
                int doctype
        ) {
            if (uploadData.getFile().getSize() == bytesWritten) {
                documentService.createUploadEntry(
                    LocalDate.now(),
                    LocalTime.now(),
                    userService.getUserId(uploadData.getEmail()),
                    fileName,
                    Paths.get(getFullPath()).toAbsolutePath().toString(),
                    doctype
                );
            }
        }

        @Override
        public String getFileExtension(MultipartFile file) {
            char[] filename = file.getOriginalFilename().toCharArray();
            StringBuilder extension = new StringBuilder();
            for (int i = filename.length - 1; i >= 0 ; i--) {
                extension.append(String.valueOf(filename[i]));
                if ('.' == filename[i]) {
                    break;
                }
            }
            return extension.reverse().toString();
        }

        @Override
        public String getUuidFilename(String fullPath, String fileExtension) {
            File destinationDir = new File(fullPath);
            if (destinationDir.exists()) {
                String uuidString;
                boolean filenameAlreadyExists = false;
                do {
                    uuidString = UUID.randomUUID().toString();
                    CharSequence uuidCharSeq = uuidString;
                    for (String filename : listFiles().getBody()) {
                        if (filename.contains(uuidCharSeq)) {
                            filenameAlreadyExists = true;
                        }
                    }
                } while (filenameAlreadyExists);
                return uuidString + fileExtension; 
            } else {
                return UUID.randomUUID().toString();
            }
        }

        @Override
        public Long copyFile(
                String fullPath,
                InputStream inputStream,
                Path destinationFile
        ) {
            File destinationDir = new File(fullPath);
            if (!destinationDir.exists()) {
                try {
                    Files.createDirectories(Paths.get(fullPath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                return Files.copy(
                        inputStream,
                        destinationFile,
                        StandardCopyOption.REPLACE_EXISTING
                );
            } catch (IOException e) {
                e.printStackTrace();
                return 0L;
            }
        }

        @Override
        public ResponseEntity<String> getUploadSuccessResponse(
                RedirectAttributes redirectAttributes,
                UploadData uploadData
        ) {
            uploadData.getModelMap().addAttribute(
                    "email",
                    uploadData.getEmail()
            );
            redirectAttributes.addFlashAttribute(
                "message",
                "You successfully uploaded "
                + uploadData.getFile().getOriginalFilename()
                + "!"
            );
            return ResponseEntity.ok().body(
                "upload ["
                + uploadData.getFile().getOriginalFilename()
                + "] received from "
                + uploadData.getModelMap().getAttribute("email").toString()
                + " on "
                + LocalDateTime.now());
        }
       
        @Override
        public ResponseEntity<String> storePic(
                RedirectAttributes redirectAttributes,
                UploadData uploadData
        ) {
            try {
                if (uploadData.getFile().isEmpty()) {
                    throw new UploadException("Failed to store empty file.");
                }
                if (isPicFiletypeValid(uploadData.getFile())) {
                    String fullPath = getPicPath(uploadData);
                    String fileName = "profile-pic.png";
                    Path destinationFile =
                            Paths.get(fullPath, "/", fileName).toAbsolutePath();
                    checkPicUploadPathInsideCurrentDir(
                            uploadData,
                            destinationFile
                    );
                    InputStream inputStream =
                            uploadData.getFile().getInputStream();
                    Long bytesWritten =
                            copyFile(fullPath, inputStream, destinationFile);
                    createDbEntryForPic(bytesWritten, uploadData, fileName);
                    return getUploadSuccessResponse(
                            redirectAttributes,
                            uploadData
                    );
                } else {
                    return ResponseEntity.badRequest().body("invalid filetype");
                }
            }
            catch (IOException e) {
                throw new UploadException("Failed to store file.", e);
            }
        }
        
        @Override
        public boolean isPicFiletypeValid(MultipartFile file) {
            switch (file.getContentType()) {
                case "image/png":
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public String getPicPath(UploadData uploadData) {
            return this.baseDir + "/" + uploadData.getEmail();
        }

        @Override
        public void checkPicUploadPathInsideCurrentDir(
                UploadData uploadData,
                Path destinationFile
        ) {
            Path fullDir = Paths.get(getPicPath(uploadData));
            if (!destinationFile.getParent().equals(fullDir.toAbsolutePath())) {
                throw new UploadException(
                    "Cannot store file outside current directory."
                );
            }
        }

        @Override
        public void createDbEntryForPic(
                Long bytesWritten,
                UploadData uploadData,
                String fileName
        ) {
            if (uploadData.getFile().getSize() == bytesWritten) {
                documentService.createUploadEntryForPic(
                        LocalDate.now(),
                        LocalTime.now(),
                        userService.getUserId(uploadData.getEmail()),
                        fileName,
                        Paths.get(getPicPath(uploadData))
                                .toAbsolutePath()
                                .toString()
                );
            }

        }


        @Override
        public ResponseEntity<Set<String>> listFiles() {
            File searchDir = new File(getFullPath().toString());
            if (searchDir.exists()) {
                String dir = getFullPath();
                return ResponseEntity.ok(Stream.of(new File(dir).listFiles())
                    .filter(file -> !file.isDirectory())
                    .map(File::getName)
                    .collect(Collectors.toSet()));
            } else {
                return ResponseEntity.noContent().build();
            }
        }

        @Override
        public Set<String> listFiles(int year, int kw) {
            String dir = getFullPath(year, kw);
            return Stream.of(new File(dir).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
        }
 
    }

    Path setBaseDir(UploadProperties properties);

    void init();
    
    ResponseEntity<UploadResponse> store(
            RedirectAttributes redirectAttributes,
            UploadData uploadData
    );

    boolean isFiletypeValid(MultipartFile file);

    String getFullPath();
    String getFullPath(int year, int kw);

    String getFileExtension(MultipartFile file);

    String getUuidFilename(String fullPath, String fileExtension);

    void checkUploadPathInsideCurrentDir(Path destinationFile);

    Long copyFile(
            String fullPath,
            InputStream inputStream,
            Path destinationFile
    );

    int getDoctype(MultipartFile file);

    void createDbEntry(
            Long bytesWritten,
            UploadData uploadData,
            String fileName,
            int type
    );

    ResponseEntity<String> getUploadSuccessResponse(
            RedirectAttributes redirectAttributes,
            UploadData uploadData
    );

    ResponseEntity<String> storePic(
            RedirectAttributes redirectAttributes,
            UploadData uploadData
    );

    boolean isPicFiletypeValid(MultipartFile file);

    String getPicPath(UploadData uploadData);

    void checkPicUploadPathInsideCurrentDir(
            UploadData uploadData,
            Path destinationFile
    );

    void createDbEntryForPic(
            Long bytesWritten,
            UploadData uploadData,
            String fileName
    );

    ResponseEntity<Set<String>> listFiles();
    Set<String> listFiles(int year, int kw);

}
