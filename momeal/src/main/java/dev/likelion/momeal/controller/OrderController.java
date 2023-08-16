package dev.likelion.momeal.controller;

import dev.likelion.momeal.dto.OrderDto;
import dev.likelion.momeal.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    @PostMapping("tickets")
    public ResponseEntity<OrderDto> readMyTicket(){
        //return new ResponseEntity<>(OrderDto, HttpStatus.OK);
        return null;
    }
}
