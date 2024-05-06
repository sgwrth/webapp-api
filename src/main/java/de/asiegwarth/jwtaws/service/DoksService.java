package de.asiegwarth.jwtaws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.asiegwarth.jwtaws.entity.Doks;
import de.asiegwarth.jwtaws.repository.DoksRepository;

public interface DoksService {
    
    @Service
    public class DoksServiceImpl implements DoksService {

        @Autowired
        DoksRepository doksRepository;

        @Override
        public List<Doks> listAllFiles(String email) {
            return doksRepository.listAllFiles(email);
        }
    }
    
    List<Doks> listAllFiles(String email);
}
