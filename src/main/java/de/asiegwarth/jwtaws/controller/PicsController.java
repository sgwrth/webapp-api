package de.asiegwarth.jwtaws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.asiegwarth.jwtaws.entity.Pics;
import de.asiegwarth.jwtaws.service.PicsService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/pics")
public class PicsController {
    
    @Autowired
    PicsService picsService;

    @CrossOrigin
    @GetMapping
    private Pics getPic() {
        return picsService.getPic();
    }
}
