package de.asiegwarth.jwtaws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.asiegwarth.jwtaws.service.SystemVarsService;

@RestController
@RequestMapping("/api/v1/system")
public class SystemVarsController {
    
    @Autowired
    SystemVarsService systemVarsServ;

    @CrossOrigin
    @GetMapping("/maxupload")
    private Integer getMaxUploadSize() {
        return systemVarsServ.getMaxUploadSize();
    }
}