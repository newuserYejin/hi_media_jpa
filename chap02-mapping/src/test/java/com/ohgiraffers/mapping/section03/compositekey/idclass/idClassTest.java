package com.ohgiraffers.mapping.section03.compositekey.idclass;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@SpringBootTest
public class idClassTest {

    @Autowired
    private CartService service;

    // 복합키가 존재하는 테이블의 매핑 전략

    /*
    * 2. @IdClass
    * - 복합키를 필드로 정의한 클래스를 이용해서 엔티티 클래스에 @IdClass 라는 어노테이션으로 매핑
    * */

    // 차이점 : 1번 방식은 복합키로 묶인 클래스를 ID로 사용하여 객체 지향적인 방식
    //       : 2번 방식은 DB와 친화적인 방식

    private static Stream<Arguments> getInfo(){
        return Stream.of(
                Arguments.of(1,1,5),
                Arguments.of(1,2,12),
                Arguments.of(2,1,3),
                Arguments.of(2,2,20)
        );
    }

    @ParameterizedTest(name = "{0}번 회원이 {1}번 책을 카트에 담기.")
    @MethodSource("getInfo")
    void testIdClass(int cartOwner, int addedBookNo, int quantity){

        CartDTO cart = new CartDTO(
                cartOwner,addedBookNo,quantity
        );

        Assertions.assertDoesNotThrow(
                () -> service.addItemToCart(cart)
        );

    }


}
