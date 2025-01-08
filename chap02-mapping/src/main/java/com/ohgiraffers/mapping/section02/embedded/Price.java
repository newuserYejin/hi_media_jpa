package com.ohgiraffers.mapping.section02.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

// 다른 곳에서 사용 가능하게 설정
@Embeddable
public class Price {

    // 정식(출판) 판매 가격, 할인율, 할인 적용 판매가
    @Column(name = "regular_price")
    private int regularPrice;

    @Column(name = "discount_rate")
    private double discountRate;

    @Column(name = "sell_price")
    private int sellPrice;

    public Price(){}

    public Price(int regularPrice, double discountRate, int sellPrice) {
        this.regularPrice = regularPrice;
        this.discountRate = discountRate;
        this.sellPrice = sellPrice;
    }

    public Price(int regularPrice, double discountRate) {
        this.regularPrice = regularPrice;
        this.discountRate = discountRate;
    }
}
