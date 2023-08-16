package dev.likelion.momeal.service;

import dev.likelion.momeal.dao.CartDao;
import dev.likelion.momeal.dto.KakaoApproveResponse;
import dev.likelion.momeal.dto.KakaoReadyResponse;
import dev.likelion.momeal.exception.BusinessLogicException;
import dev.likelion.momeal.exception.ExceptionCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.springframework.security.config.Elements.JWT;

@Service
@RequiredArgsConstructor
@Transactional
public class KakaoPayService {
    static final String cid = "TC0ONETIME"; // 가맹점 테스트 코드
    static final String admin_Key = "d977d90a404ef1993971337228f56e77";
    private KakaoApproveResponse kakaoApproveResponse;
    private KakaoReadyResponse kakaoReady;
    private final CartDao cartDao;

    public static Claims decodeToken(String pgToken, String secretKey) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(admin_Key)
                    .parseClaimsJws(pgToken)
                    .getBody();
            return claims;
        } catch (Exception e) {
            // 예외 처리
            throw new BusinessLogicException(ExceptionCode.INVALID_TOKEN);
        }
    }


    public KakaoReadyResponse kakaoPayReady(KakaoApproveResponse kakaoApproveResponse) {
        //장바구니 생성
        this.cartDao.createCart(Integer.valueOf(kakaoApproveResponse.getItem_name()), kakaoApproveResponse.getQuantity(), kakaoApproveResponse.getPartner_user_id());

        // 카카오페이 요청 양식
        MultiValueMap parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("partner_order_id", kakaoApproveResponse.getPartner_order_id());
        parameters.add("partner_user_id", kakaoApproveResponse.getPartner_user_id());
        parameters.add("item_name", kakaoApproveResponse.getItem_name());
        parameters.add("quantity", kakaoApproveResponse.getQuantity());
        parameters.add("total_amount", kakaoApproveResponse.getTotal_amount());
        parameters.add("tax_free_amount",kakaoApproveResponse.getTax_free_amount());
        parameters.add("approval_url", "http://localhost:8088/kakao/success"); // 성공 시 redirect url
        parameters.add("cancel_url", "http://localhost:8088/kakao/cancel"); // 취소 시 redirect url
        parameters.add("fail_url", "http://localhost:8088/kakao/fail"); // 실패 시 redirect url

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        kakaoReady = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/ready",
                requestEntity,
                KakaoReadyResponse.class);

        return kakaoReady;
    }

    /**
     * 결제 완료 승인
     */
    public KakaoApproveResponse ApproveResponse(String pgToken) {

        Claims claims = decodeToken(pgToken, admin_Key);

        String partnerOrderId = claims.get("partner_order_id", String.class);
        String partnerUserId = claims.get("partner_user_id", String.class);
        System.out.println(partnerUserId);
        System.out.println(partnerOrderId);

        // 카카오 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tid", kakaoReady.getTid());
        parameters.add("partner_order_id", "주문 아이디");
        parameters.add("partner_user_id", "momeal@naver.com");
        parameters.add("pg_token", pgToken);

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        KakaoApproveResponse approveResponse = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/approve",
                requestEntity,
                KakaoApproveResponse.class);

        return approveResponse;
    }

    /**
     * 카카오 요구 헤더값
     */
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        String auth = "KakaoAK " + admin_Key;

        httpHeaders.set("Authorization", auth);
        httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return httpHeaders;
    }

}
