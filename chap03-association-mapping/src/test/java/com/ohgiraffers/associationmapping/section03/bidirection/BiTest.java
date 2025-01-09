package com.ohgiraffers.associationmapping.section03.bidirection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@SpringBootTest
public class BiTest {

    @Autowired
    private Biservice service;

    // 외래키 하나로 양방향 조회 가능
    // 하지만 객체 입장에서는 서로 다른 두 단방향으로 참조

    // 연관관계의 주인을 설정하는 기준 : 외래키를 가지고 있는 엔티티가 Owner 로 설정된다.

    @DisplayName("양방향 연관관계 매핑 조회")
    @Test
    void ownerFindTest(){

        int menuCode = 10;

        // Menu 엔티티가 주인인 관계에서 조회 할 때는 바로 결과를 가져온다

        Menu foundMenu = service.findMenu(menuCode);

        System.out.println("foundMenu = " + foundMenu);

        Assertions.assertEquals(menuCode,foundMenu.getMenuCode());
    }

    @DisplayName("양방향 연관관계 매핑 조회(주인이 아닌경우)")
    @Test
    void slaveFindTest(){

        int categoryCode = 10;

        Category foundCategory = service.findCategory(categoryCode);

//        System.out.println("foundCategory = " + foundCategory.getMenuList());

        Assertions.assertEquals(categoryCode,foundCategory.getCategoryCode());
    }

    private static Stream<Arguments> getMenuInfo(){
        return Stream.of(
                Arguments.of(111,"스테이크",9800,"Y")
        );
    }

    @DisplayName("양방향 연관관계 주인 객체를 이용한 삽입 테스트")
    @ParameterizedTest
    @MethodSource("getMenuInfo")
    void ownerInsert(int menuCode, String menuName, int menuPrice, String orderableStatus){

        // 메뉴에 넣을 카테고리 객체
        Category category = service.findCategory(4);

        Menu newMenu = new Menu(menuCode,menuName,menuPrice,category,orderableStatus);

        Assertions.assertDoesNotThrow(
                () -> service.registMenu(newMenu)
        );

    }

    private static Stream<Arguments> getCategoryInfo(){
        return Stream.of(
                Arguments.of(112,"양방향 테스트", null)
        );
    }

    @DisplayName("양방향 연관관계 주인이 아닌 객체 이용한 insert")
    @ParameterizedTest
    @MethodSource("getCategoryInfo")
    void slaveInsert(int categoryCode, String categoryName, Integer ref){

        Category category = new Category(categoryCode,categoryName,ref);

        service.registCategory(category);

        Category foundCategory = service.findCategory(categoryCode);

        Assertions.assertNotNull(foundCategory);
    }


}
