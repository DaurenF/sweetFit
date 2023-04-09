package kz.sweet.fit.services;

import kz.sweet.fit.exceptions.UsernameNotUniqueException;
import kz.sweet.fit.models.UserEntity;
import kz.sweet.fit.repositories.UserEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class RegistrationService {
    @Autowired
    UserEntityRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public void register(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public Boolean checkUsernameExists(String username){
        Optional<UserEntity> user = userRepository.findUserByUsername(username);
        if(user.isPresent()){
            return true;
        }else return false;
    }

}
