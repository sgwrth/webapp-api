package de.asiegwarth.jwtaws.controller;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.asiegwarth.jwtaws.entity.Doks;
import de.asiegwarth.jwtaws.entity.DoksCommand;
import de.asiegwarth.jwtaws.repository.DoksRepository;
import de.asiegwarth.jwtaws.service.DoksService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/docs")
public class DoksController {
    
    @Autowired
    DoksService doksService;

    @Autowired
    DoksRepository doksRepo;
    
    @CrossOrigin
    @GetMapping
    private List<Doks> listAllFiles() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName(); 
        return doksService.listAllFiles(email);
    }

    @CrossOrigin
    @GetMapping("/pag")
    private ResponseEntity<Map<String, Object>> getAllFiles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName(); 

        List<Doks> doks = new ArrayList<Doks>();
        Pageable paging = PageRequest.of(page, size);
        Page<Doks> pageDoks;
        pageDoks = doksRepo.findByEmail(email, paging);
        doks = pageDoks.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("doks", doks);
        response.put("currentPage", pageDoks.getNumber());
        response.put("totalItems", pageDoks.getTotalElements());
        response.put("totalPages", pageDoks.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/download")
    private ResponseEntity<byte[]> loadFile(
            @RequestBody DoksCommand doksCommand
    ) throws IOException {
        String file = Paths.get(
                doksCommand.getPath(),
                "\\",
                doksCommand.getName()
        ).toString();
        FileSystemResource res = new FileSystemResource(file);
        System.out.println(file);
        return new ResponseEntity<byte[]>(
                res.getContentAsByteArray(),
                HttpStatus.CREATED
        );
    }
    
}
