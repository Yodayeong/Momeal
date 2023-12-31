package dev.likelion.momeal.controller;

import dev.likelion.momeal.dto.KakaoApproveResponse;
import dev.likelion.momeal.dto.KakaoReadyResponse;
import dev.likelion.momeal.dto.UserDto;
import dev.likelion.momeal.exception.BusinessLogicException;
import dev.likelion.momeal.exception.ExceptionCode;
import dev.likelion.momeal.repository.OrderRepository;
import dev.likelion.momeal.service.KakaoPayService;
import dev.likelion.momeal.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("kakao")
public class KakaoController {

    private static final Logger logger = LoggerFactory.getLogger(KakaoController.class);
    private final KakaoPayService kakaoPayService;
    private final OrderService orderService;

    // 결제 버튼 클릭 시 호출( API TEST용)

    /**
     * 결제요청
     */
    @CrossOrigin
    @PostMapping("/ready")
    public ResponseEntity<KakaoReadyResponse> readyToKakaoPay(@RequestBody KakaoApproveResponse kakaoApproveResponse) {
        KakaoReadyResponse response = kakaoPayService.kakaoPayReady(kakaoApproveResponse);
        return ResponseEntity.ok(response);
    }
    /**
     * 결제 진행 중 취소
     */
    @CrossOrigin
    @GetMapping("/cancel")
    public void cancel() {

        throw new BusinessLogicException(ExceptionCode.PAY_CANCEL);
    }

    /**
     * 결제 실패
     */
    @CrossOrigin
    @GetMapping("/fail")
    public void fail() {
        throw new BusinessLogicException(ExceptionCode.PAY_FAILED);
    }

    /**
    * 결제성공
     */
    @CrossOrigin
    @GetMapping("/success")
    public ResponseEntity afterPayRequest(
            @RequestParam("pg_token") String pgToken
    ){

//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String username = ((UserDetails) principal).getUsername();
//        System.out.println(username);

        KakaoApproveResponse kakaoApprove = kakaoPayService.ApproveResponse(pgToken); // token으로 승인처리
        orderService.KakaoResponseToOrder(kakaoApprove); // order_Entity 테이블에 저장

        return new ResponseEntity<>(kakaoApprove,HttpStatus.OK);

    }


}
