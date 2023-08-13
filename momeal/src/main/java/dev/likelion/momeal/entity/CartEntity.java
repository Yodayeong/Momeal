package dev.likelion.momeal.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class CartEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int amount;

    @OneToMany(
            targetEntity = TicketEntity.class,
            fetch = FetchType.LAZY,
            mappedBy = "cartEntity"
    )
    private List<TicketEntity> ticketEntityList = new ArrayList<>();

    @ManyToOne(
            targetEntity = UserEntity.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "userid")
    private UserEntity userEntity;

}
