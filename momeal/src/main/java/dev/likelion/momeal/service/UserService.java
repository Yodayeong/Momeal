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
    public boolean authenticateUser(String email, String password) {
        // 사용자 아이디로 데이터베이스에서 사용자 정보를 조회
        UserEntity userEntity = userRepository.findByEmail(email);

        // 사용자 정보가 존재하고, 입력된 비밀번호가 인코딩 정보와 일치하는지 확인
       if(userEntity != null && passwordEncoder.matches(password, userEntity.getPassword())){
           return true;
       }
        return false; // 인증 실패
    }

    public ResponseEntity signupRequest(UserDto dto){

        UserEntity newUser = new UserEntity();
        newUser.setUserName(dto.getUserName());
        newUser.setEmail(dto.getEmail());
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        newUser.setRole(false); // 회원가입한 유저들은 role이 무조건 false , 직접 DB에 넣어주는 관리자는 True
        userRepository.save(newUser);

        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity<Boolean> duplicateCheckRequest(String email) {
        boolean isDuplicated = userRepository.existsByEmail(email);
        return new ResponseEntity<>(isDuplicated,HttpStatus.OK);
    }

}
