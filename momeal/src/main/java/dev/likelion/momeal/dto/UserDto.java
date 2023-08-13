package dev.likelion.momeal.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserDto {

    private String userName;
    private String password;
    private String passwordCheck;
    private String email;
    private boolean role;

}

