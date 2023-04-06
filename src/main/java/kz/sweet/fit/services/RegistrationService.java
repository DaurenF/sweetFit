package kz.sweet.fit.services;

import kz.sweet.fit.models.UserEntity;
import kz.sweet.fit.repositories.UserEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

}
