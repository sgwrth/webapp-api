package de.asiegwarth.jwtaws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.asiegwarth.jwtaws.entity.User;
import de.asiegwarth.jwtaws.repository.UserRepository;

public interface UserService {

    @Service
    public class UserServiceImpl implements UserService {

        @Autowired
        UserRepository userRepo;

        @Override
        public Long getUserId(String email) {
            User user = userRepo.findByEmail(email).get();
            return user.getId();
        }

    }
    
    Long getUserId(String email);
}
