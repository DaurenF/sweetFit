package kz.sweet.fit.services;

import kz.sweet.fit.models.entity.UserEntity;
import kz.sweet.fit.repositories.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

    private final UserEntityRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void register(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public Boolean checkUsernameExists(String username) {
        Optional<UserEntity> user = userRepository.findUserByUsername(username);
        return user.isPresent();
    }

    @Async
    public void longTask() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.warn("Long task actually finished");
    }
}
