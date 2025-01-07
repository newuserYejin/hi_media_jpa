package com.ohgiraffers.section02.crud;

import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

public class EntityManagerCRUDTest {

    // Test 클래스는 검증할 클래스의 인스턴스를 생성해서 테스트 하는 용도로 사용

    private EntityManagerCRUD crud;

    @BeforeEach
    void initManager(){

        // 테스트 메소드 실행전 crud 클래스 인스턴스 생성
        this.crud = new EntityManagerCRUD();
    }

    @AfterEach
    void rollback(){
        EntityTransaction transaction = crud.getManagerInstance().getTransaction();

        // 모든 권한은 entity 관리할 수 있게 manager 에게 넘긴다.
//        transaction.rollback();
    }

    // 값을 넘겼을 때 테스트 통과 여부 확인하는 Test
    // ParameterizedTest 는 경우의 수 만큼 반복해야하는 작업을 줄여주는게 가능하다.
    // 파라미터로 전달할 값을 목록 만큼 반복적으로 테스트 메소드를 실행해준다.
    @ParameterizedTest
    @DisplayName("매개변수 전달하여 테스트")
    // 여러개의 테스트 전용 파라미터를 전달, 쉼표로 값 구분
    @CsvSource({"1,1","2,2","3,3"})
    void testFindByCode(int menuCode, int expected){
        Menu foundMenu = crud.findMenuByMenuCode(menuCode);

        System.out.println("foundMenu = " + foundMenu);

        Assertions.assertEquals(expected,foundMenu.getMenuCode());
    }

    private static Stream<Arguments> newMenu(){
        return Stream.of(
                Arguments.of(
                        "불고기백반",
                        11000,
                        4,
                        "Y"
                )
        );
    }

    @ParameterizedTest
    @DisplayName("새로운 메뉴 insert Test")
    @MethodSource("newMenu")
    void testInsertNewMenu(String name, int price, int code, String orderableStatus){

        Menu newMenu = new Menu(name,price,code,orderableStatus);

        System.out.println("newMenu = " + newMenu);

        Long count = crud.saveAndReturnCount(newMenu);

        Assertions.assertNotEquals(22, count);
    }

    @ParameterizedTest
    @DisplayName("메뉴 이름 수정 테스트")
    @CsvSource("27, 우삼겹백반")
    void modifyTestMenu(int code, String name){
        Menu modifyMenu = crud.modifyMenuName(code,name);

        Assertions.assertEquals(name,modifyMenu.getMenuName());
    }

    @ParameterizedTest
    @DisplayName("메뉴 코드로 메뉴 삭제")
    @ValueSource(ints = {26})
    void testRemoveResult(int code){
        Long count = crud.removeAndReturnCount(code);

        Assertions.assertEquals(22,count);
    }

}
