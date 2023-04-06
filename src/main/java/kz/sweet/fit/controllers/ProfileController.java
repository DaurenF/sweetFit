package kz.sweet.fit.controllers;

import kz.sweet.fit.models.UserEntity;
import kz.sweet.fit.security.JWTUtil;
import kz.sweet.fit.services.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@Slf4j
public class ProfileController {
    @Autowired
    ProfileService profileService;
    @Autowired
    JWTUtil jwtUtil;

    @GetMapping("/get")
    public ResponseEntity<UserEntity> getProfile(@RequestHeader("Authorization") String token){
        log.info("Client request : /get");
        String username = jwtUtil.validateTokenAndRetrieveClaim(token.substring(7, token.length()));
        return ResponseEntity.ok(profileService.getProfileByUsername(username));
    }

    @PutMapping("/update")
    public ResponseEntity<UserEntity> updateProfile(@RequestHeader("Authorization") String token, @RequestBody UserEntity userEntity){
        log.info("Client request : /update");
        String username = jwtUtil.validateTokenAndRetrieveClaim(token.substring(7, token.length()));
        return ResponseEntity.ok(profileService.updateProfile(username, userEntity));
    }

    @PatchMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestHeader("Authorization")String token ,String password){
        String username = jwtUtil.validateTokenAndRetrieveClaim(token.substring(7, token.length()));
        profileService.updatePassword(username, password);
        return ResponseEntity.ok("Password updated!");
    }

    @PatchMapping("/update-username")
    public ResponseEntity<String> updateUsername(@RequestHeader("Authorization")String token ,String new_username){
        String username = jwtUtil.validateTokenAndRetrieveClaim(token.substring(7, token.length()));
        profileService.updateUsername(username, new_username);
        return ResponseEntity.ok("Password updated!");
    }




}