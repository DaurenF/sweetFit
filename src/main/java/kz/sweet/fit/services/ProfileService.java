package kz.sweet.fit.services;

import kz.sweet.fit.exceptions.UserNotFoundException;
import kz.sweet.fit.models.UserEntity;
import kz.sweet.fit.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    @Autowired
    UserEntityRepository userEntityRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    public UserEntity getProfileByUsername(String username){
        return userEntityRepository.findUserByUsername(username).get();
    }
    public UserEntity updateProfile(String username, UserEntity userEntity){
        UserEntity user = userEntityRepository.findUserByUsername(username).get();
        user.setPassword(userEntity.getPassword());
        user.setName(userEntity.getName());
        user.setBirth(userEntity.getBirth());
        user.setSex(userEntity.getSex());
        user.setHeight(userEntity.getHeight());
        user.setWeight(userEntity.getWeight());
        user.setLastname(userEntity.getLastname());
        userEntityRepository.save(user);
        return user;
    }
    public Boolean updatePassword(String username,String password){
        UserEntity user = userEntityRepository.findUserByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found", 404));
        user.setPassword(passwordEncoder.encode(password));
        return true;
    }

    public Boolean updateUsername(String username,String new_username){
        UserEntity user = userEntityRepository.findUserByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found", 404));
        user.setUsername((new_username));
        return true;
    }


}
