package dev.likelion.momeal.controller;

import dev.likelion.momeal.config.PasswordEncoderConfig;
import dev.likelion.momeal.dto.UserDto;
import dev.likelion.momeal.entity.UserEntity;
import dev.likelion.momeal.repository.UserRepository;
import dev.likelion.momeal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto dto){
        boolean isAuthenticated = userService.authenticateUser(dto.getUserid(),dto.getPassword());

        if (isAuthenticated) {
            return new ResponseEntity<>("로그인 성공", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("로그인 실패", HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("signup")
    public ResponseEntity<String> signupRequest(
            @RequestBody UserDto dto
    ){
        return userService.signupRequest(dto);
    }



}
