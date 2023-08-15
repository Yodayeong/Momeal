package dev.likelion.momeal.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/*
*
* 결제 승인 요청 시 사용*/
@Getter
@Setter
@ToString
public class KakaoApproveResponse {

    private String aid; // 요청 고유 번호
    private String tid; // 결제 고유 번호
    private String partner_order_id; // 가맹점 주문 번호
    private String partner_user_id; // 가맹점 회원 id
    private String payment_method_type; // 결제 수단
    private int total_amount; // 결제 금액 정보
    private String item_name; // 상품명
    private int quantity; // 상품 수량
    private String created_at; // 결제 요청 시간
    private String approved_at; // 결제 승인 시간
    private String payload; // 결제 승인 요청에 대해 저장 값, 요청 시 전달 내용

}
