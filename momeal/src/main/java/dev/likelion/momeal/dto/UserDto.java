package dev.likelion.momeal.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserDto {

    private String username;
    private String userid;
    private String password;
    private String passwordCheck;
    private String email;

}

