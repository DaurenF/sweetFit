package kz.sweet.fit.services;

import kz.sweet.fit.models.UserEntity;
import kz.sweet.fit.repositories.UserEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@Slf4j
public class RegistrationService {
    @Autowired
    UserEntityRepository userRepository;

    public int register(UserEntity user){
        try{
            userRepository.save(user);
            return 1;
        }
        catch (Exception e){
            log.error(e.toString());
            return 0;
        }

    }

}
