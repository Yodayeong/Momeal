package dev.likelion.momeal.service;

import dev.likelion.momeal.dto.UserDto;
import dev.likelion.momeal.entity.UserEntity;
import dev.likelion.momeal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public boolean authenticateUser(String userid, String password) {
        // 사용자 아이디로 데이터베이스에서 사용자 정보를 조회
        UserEntity userEntity = userRepository.findByUserid(userid);

        // 사용자 정보가 존재하고, 입력된 비밀번호가 인코딩 정보와 일치하는지 확인
       if(userEntity != null && passwordEncoder.matches(password, userEntity.getPassword())){
           return true;
       }
        return false; // 인증 실패
    }

    public ResponseEntity<String> signupRequest(UserDto dto){
        if(!dto.getPassword().equals(dto.getPasswordCheck())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        UserEntity newUser = new UserEntity();
        newUser.setUsername(dto.getUsername());
        newUser.setUserid(dto.getUserid());
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        newUser.setEmail(dto.getEmail());
        userRepository.save(newUser);

        return new ResponseEntity<>("회원가입 성공",HttpStatus.OK);
    }
}
