package de.asiegwarth.jwtaws.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.asiegwarth.jwtaws.entity.UploadData;
import de.asiegwarth.jwtaws.entity.UploadResponse;
import de.asiegwarth.jwtaws.service.UploadService;
import de.asiegwarth.jwtaws.upload.UploadFileNotFoundException;

@RestController
public class UploadController {
   
    @Autowired
    UploadService uploadService;

    @CrossOrigin
    @GetMapping("api/v1/upload")
	public Set<String> listFiles(@Param("year") int year, @Param("kw") int kw) {
        return uploadService.listFiles(year, kw);
    }

    @CrossOrigin
    @PostMapping("api/v1/upload")
	public ResponseEntity<UploadResponse> handleFileUpload(
            RedirectAttributes redirectAttributes,
            UploadData uploadData
    ) {
        return uploadService.store(redirectAttributes, uploadData);
	}

    @CrossOrigin
    @PostMapping("api/v1/upload/pic")
	public ResponseEntity<String> handlePicUpload(
            RedirectAttributes redirectAttributes,
            UploadData uploadData
    ) {
        return uploadService.storePic(redirectAttributes, uploadData);
	}

	@ExceptionHandler(UploadFileNotFoundException.class)
	public ResponseEntity<?> handleUploadFileNotFound(UploadFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
