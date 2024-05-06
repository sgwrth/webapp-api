package de.asiegwarth.jwtaws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.asiegwarth.jwtaws.repository.SystemVarsRepository;

public interface SystemVarsService {
    
    @Service
    public class SystemVarsServiceImpl implements SystemVarsService {

        @Autowired
        SystemVarsRepository systemVarsRepo;

        @Override
        public Integer getMaxUploadSize() {
            Integer maxSize = Integer.valueOf(systemVarsRepo.getMaxUploadSize());
            System.out.println("Max file size in MiB: " + maxSize / (1024 * 1024));
            return maxSize;
        }
    }

    Integer getMaxUploadSize();
}
