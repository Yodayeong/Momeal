package dev.likelion.momeal.controller;

import dev.likelion.momeal.dto.OrderDto;
import dev.likelion.momeal.dto.UserDto;
import dev.likelion.momeal.dto.UserTickets;
import dev.likelion.momeal.entity.OrderEntity;
import dev.likelion.momeal.entity.UserEntity;
import dev.likelion.momeal.repository.OrderRepository;
import dev.likelion.momeal.repository.UserRepository;
import dev.likelion.momeal.service.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    @CrossOrigin
    @PostMapping("/myPage/tickets")
    public ResponseEntity<List> readMyTicket(@RequestBody UserDto dto){
        // 이메일 값 가져오기
        String email = dto.getEmail();

        // 이메일을 기준으로 유저를 찾기
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            // 유저가 없을 경우 처리
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<OrderEntity> tickets = orderRepository.findByEmail(email);

        List<UserTickets> orderList = new ArrayList<>();
        for (OrderEntity ticket : tickets) {
            UserTickets userTickets = new UserTickets();
            userTickets.setPrice(ticket.getPrice());
            userTickets.setQuantity(ticket.getQuantity());
            // 필요한 다른 정보도 추가할 수 있음
            orderList.add(userTickets);
        }

        // OrderDto 리스트를 ResponseEntity에 담아 반환
        return ResponseEntity.ok(orderList);
    }
    @CrossOrigin
    @GetMapping("/tickets/prices")
    public ResponseEntity<List> readTicketType(){
        List<Integer> prices = new ArrayList<>();
        prices.add(3000);
        prices.add(4500);
        prices.add(5000);
        prices.add(6000);

        return ResponseEntity.ok(prices);
    }
}
