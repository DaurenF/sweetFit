package kz.sweet.fit.services;

import kz.sweet.fit.exceptions.UserNotFoundException;
import kz.sweet.fit.models.UserEntity;
import kz.sweet.fit.repositories.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserEntityRepository userEntityRepository;

    private final PasswordEncoder passwordEncoder;

    public UserEntity getProfileByUsername(String username) {
        return userEntityRepository.findUserByUsername(username).orElse(null);
    }

    public UserEntity updateProfile(String username, UserEntity userEntity) {
        Optional<UserEntity> wrapper = userEntityRepository.findUserByUsername(username);
        if (wrapper.isPresent()) {
            UserEntity user = wrapper.get();
            user.setPassword(userEntity.getPassword());
            user.setName(userEntity.getName());
            user.setBirth(userEntity.getBirth());
            user.setSex(userEntity.getSex());
            user.setHeight(userEntity.getHeight());
            user.setWeight(userEntity.getWeight());
            user.setLastname(userEntity.getLastname());
            userEntityRepository.save(user);
            return user;
        } else {
            throw new UserNotFoundException("User not found", 500);
        }

    }

    public void updatePassword(String username, String password) {
        UserEntity user = userEntityRepository.findUserByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found", 404));
        user.setPassword(passwordEncoder.encode(password));
    }

    public void updateUsername(String username, String new_username) {
        UserEntity user = userEntityRepository.findUserByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found", 404));
        user.setUsername((new_username));
    }


}
