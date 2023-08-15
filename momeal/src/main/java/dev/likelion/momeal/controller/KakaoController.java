package dev.likelion.momeal.controller;

import dev.likelion.momeal.dto.KakaoApproveResponse;
import dev.likelion.momeal.dto.KakaoReadyResponse;
import dev.likelion.momeal.dto.UserDto;
import dev.likelion.momeal.service.KakaoPayService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("kakao")
public class KakaoController {

    private static final Logger logger = LoggerFactory.getLogger(KakaoController.class);
    private final KakaoPayService kakaoPayService;
    // 결제 버튼 클릭 시 호출

    /**
     * 결제요청
     */

    @PostMapping("/ready")
    public ResponseEntity<KakaoReadyResponse> readyToKakaoPay() {
        KakaoReadyResponse response = kakaoPayService.kakaoPayReady();
        return ResponseEntity.ok(response);
    }

    /**
    * 결제성공
     */

    @GetMapping("/success")
    public ResponseEntity<KakaoApproveResponse> afterPayRequest(@RequestParam("pg_token") String pgToken){
        KakaoApproveResponse kakaoApprove = kakaoPayService.ApproveResponse(pgToken);
        return ResponseEntity.ok(kakaoApprove);
    }






}
