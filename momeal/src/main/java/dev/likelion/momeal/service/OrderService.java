package dev.likelion.momeal.service;

import dev.likelion.momeal.dto.KakaoApproveResponse;
import dev.likelion.momeal.dto.KakaoReadyResponse;
import dev.likelion.momeal.entity.OrderEntity;
import dev.likelion.momeal.entity.UserEntity;
import dev.likelion.momeal.repository.OrderRepository;
import dev.likelion.momeal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    public void KakaoResponseToOrder(KakaoApproveResponse kakaoApproveResponse){
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setPrice(kakaoApproveResponse.getTotal_amount()); // 가격

        UserEntity user = userRepository.findByEmail(kakaoApproveResponse.getPartner_user_id()); //userEmail
        orderEntity.setUserEntity(user);

//        String orderDate = kakaoApproveResponse.getApproved_at();
//        orderEntity.setOrderDate(orderDate.substring(0, kakaoApproveResponse.getApproved_at().indexOf('T')));
        orderEntity.setQuantity(kakaoApproveResponse.getQuantity());
        orderRepository.save(orderEntity);
    }
}
