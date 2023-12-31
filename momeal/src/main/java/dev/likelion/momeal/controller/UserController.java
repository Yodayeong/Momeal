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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    // 로그인
    @PostMapping("login")
    @CrossOrigin
    public ResponseEntity<List> login(@RequestBody UserDto dto){
        boolean isAuthenticated = userService.authenticateUser(dto.getEmail(),dto.getPassword());

        List<Object> userInfo = new ArrayList<>();
        UserEntity user = userRepository.findByEmail(dto.getEmail());

        userInfo.add(user.getUserName());
        userInfo.add(dto.isRole());

        if (isAuthenticated) {
            return new ResponseEntity<>(userInfo,HttpStatus.OK); // 200
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401
        }

    }
    // 회원가입
    @PostMapping("signup")
    @CrossOrigin
    public ResponseEntity signupRequest(
            @RequestBody UserDto dto
    ){
        return userService.signupRequest(dto);
    }

    // 이메일 중복확인
    @PostMapping("signup/duplicatecheck")
    @CrossOrigin
    public ResponseEntity<Boolean> duplicatedEmailCheck(@RequestBody UserDto dto){
        return userService.duplicateCheckRequest(dto.getEmail());
    }
}
