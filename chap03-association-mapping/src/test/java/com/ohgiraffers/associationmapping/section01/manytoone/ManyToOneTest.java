package com.ohgiraffers.associationmapping.section01.manytoone;

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
public class ManyToOneTest {

    @Autowired
    private ManyToOneService service;

    // Entity 클래스 간의 관계 = 연관성을 매핑하는 것을 의미한다.

    // 다중성에 의한 분류 : 연관 관계가 있늕 객체 관계에서 실제로 연관을 가지는 객체의 수에 따라 분류된다.

    /*
    * - 1:1 (OneToOne)
    * - 1:N (OneToMany)
    * - N:1 (ManyToOne)
    * - N:N (ManyToMany)
    * */

    // ManyToOne 은 다수의 엔티티(메뉴)가 하나의 엔티티(카테고리)를 참조하는 상황에서 사용

    // 연관 관계 형성 엔티티 조회 방법
    /*
    * 1. 객체 그래프 탐색(객체 연관 관계를 사용한 조회)
    * 2. 객체 지향 쿼리(JPQL) 사용
    * */

    // 1번 방식 확인
    @DisplayName("N:1 관계의 객체 그래프 탐색을 이용한 조회")
    @Test
    void manyToOneFind(){

        // 테스트용 값
        int menuCode = 10;

        Menu foundMenu = service.findMenu(menuCode);

        Category category = foundMenu.getCategory();

        System.out.println("foundMenu = " + foundMenu);
        System.out.println("category = " + category);

        Assertions.assertNotNull(category);

    }
    
    @DisplayName("N:1 연관관계 객체지향쿼리 이용 테스트")
    @Test
    void manyToOneJPQL(){
        int menuCode = 11;
        
        String categoryName = service.findCategoryName(menuCode);

        System.out.println("categoryName = " + categoryName);

        Assertions.assertNotNull(categoryName);
    }

    //commit() 시 insert 붜리 문이 동작하는데, PERSIST 로 인해 Category 도 insert 해야한다.

    private static Stream<Arguments> getInfo(){
        return Stream.of(
                Arguments.of(100,"돈까스 덮밥",30000,132,"퓨전 음식","Y")
        );
    }

    @DisplayName("N:1 연관관계 객체 삽입 테스트")
    @ParameterizedTest
    @MethodSource("getInfo")
    void manyToOneInsert(int menuCode, String menuName, int menuPrice, int categoryCode, String categoryName, String orderableStatus){
        MenuDTO menuInfo = new MenuDTO(menuCode, menuName, menuPrice,
                new CategoryDTO(categoryCode, categoryName, null),
                orderableStatus
        );

        Assertions.assertDoesNotThrow(
                ()-> service.registMenu(menuInfo)
        );

    }

}
