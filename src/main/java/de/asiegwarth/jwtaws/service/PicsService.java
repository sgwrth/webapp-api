package de.asiegwarth.jwtaws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import de.asiegwarth.jwtaws.entity.Pics;
import de.asiegwarth.jwtaws.repository.PicsRepository;

public interface PicsService {

    @Service
    public class PicsServiceImpl implements PicsService {

        @Autowired
        PicsRepository picsRepository;

        @Override
        public Pics getPic() {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            return picsRepository.findByEmail(email);
        }

    }

    Pics getPic();
    
}
