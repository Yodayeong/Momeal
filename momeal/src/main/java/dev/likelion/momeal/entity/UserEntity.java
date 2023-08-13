package dev.likelion.momeal.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@RequiredArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id
    private String email; // 사용자 이메일(아이디)

    private String userName; // 사용자 이름
    private String password; // 사용자 비밀번호
    private boolean role; // 관리자 여부

    @OneToMany(
            targetEntity = OrderEntity.class,
            fetch = FetchType.LAZY,
            mappedBy = "userEntity"
    )
    private List<OrderEntity> orderEntityList = new ArrayList<>();

}
