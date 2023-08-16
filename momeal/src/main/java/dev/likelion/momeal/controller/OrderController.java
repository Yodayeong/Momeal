package dev.likelion.momeal.controller;

import dev.likelion.momeal.dto.OrderDto;
import dev.likelion.momeal.dto.UserDto;
import dev.likelion.momeal.entity.OrderEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {


    @PostMapping("/myPage/tickets")
    public ResponseEntity<OrderDto> readMyTicket(@RequestBody UserDto dto){

        //return new ResponseEntity<>(OrderDto, HttpStatus.OK);
        return null;
    }

    @GetMapping("/tickets/prices")
    public ResponseEntity<List> readTicketType(){
        return null;
    }
}
