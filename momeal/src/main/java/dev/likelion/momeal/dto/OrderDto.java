package dev.likelion.momeal.dto;

import dev.likelion.momeal.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Getter
@Setter
public class OrderDto {

    private Long orderId;
    private int price;
    private String orderDate;
    private String email;

}
