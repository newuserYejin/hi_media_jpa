package com.ohgiraffers.section03.entity;

import com.ohgiraffers.section01.entitymanager.EntityManagerGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class LifeCycleTest {

    private EntityLifeCycle lifeCycle;

    @BeforeEach
    void setLifeCycle(){
        this.lifeCycle = new EntityLifeCycle();
    }

    @ParameterizedTest
    @DisplayName("비영속 테스트")
    @ValueSource(ints={1,2})
    void testNotManaged(int menuCode){
        Menu foundMenu = lifeCycle.findMenuByMenuCode(menuCode);

        Menu newMenu = new Menu(
                foundMenu.getMenuCode(),
                foundMenu.getMenuName(),
                foundMenu.getMenuPrice(),
                foundMenu.getCategoryCode(),
                foundMenu.getOrderableStatus()
        );

        Assertions.assertNotEquals(foundMenu,newMenu);

    }

    @ParameterizedTest
    @DisplayName("다른 엔티티 manager가 관리하는 영속성 테스트")
    @ValueSource(ints={1,2})
    void testOtherManager(int menuCode){

        //
        Menu menu1 = lifeCycle.findMenuByMenuCode(menuCode);
        Menu menu2 = lifeCycle.findMenuByMenuCode(menuCode);

        Assertions.assertNotEquals(menu1,menu2);
    }

    @ParameterizedTest
    @DisplayName("같은 엔티티 manager가 관리하는 영속성 테스트")
    @ValueSource(ints={1,2})
    void testSameManager(int menuCode){
        EntityManager manager = EntityManagerGenerator.getInstance();

        Menu menu1 = manager.find(Menu.class,menuCode);
        Menu menu2 = manager.find(Menu.class,menuCode);

        Assertions.assertEquals(menu1,menu2);
    }

    @ParameterizedTest
    @DisplayName("준영속화 detach 테스트")
    @CsvSource({"11,1000","12,1000"})
    void testDetachEntity(int menuCode, int menuPrice){

        EntityManager manager = EntityManagerGenerator.getInstance();

        EntityTransaction transaction = manager.getTransaction();

        Menu foundMEnu = manager.find(Menu.class,menuCode);

        transaction.begin();

        // detach : 특정 엔티티만 준영속 상태로 변경
        manager.detach(foundMEnu);
        foundMEnu.setMenuPrice(menuPrice);
        manager.flush();

        Assertions.assertEquals(menuPrice, manager.find(Menu.class,menuCode).getMenuPrice());

        transaction.rollback();
    }


    @ParameterizedTest
    @DisplayName("준영속화 detach후 재영속화 테스트")
    @CsvSource({"11,1000","12,1000"})
    void testDetachAndPersist(int menuCode, int menuPrice){

        EntityManager manager = EntityManagerGenerator.getInstance();

        EntityTransaction transaction = manager.getTransaction();

        Menu foundMEnu = manager.find(Menu.class,menuCode);

        transaction.begin();

        // detach : 특정 엔티티만 준영속 상태로 변경
        manager.detach(foundMEnu);
        foundMEnu.setMenuPrice(menuPrice);
        manager.flush();

        Assertions.assertNotEquals(menuPrice, manager.find(Menu.class,menuCode).getMenuPrice());

        // 재 영속화
        manager.merge(foundMEnu);
//        foundMEnu.setMenuPrice(menuPrice);
        manager.flush();

        Assertions.assertEquals(menuPrice, manager.find(Menu.class,menuCode).getMenuPrice());

        transaction.rollback();
    }
    
    @ParameterizedTest
    @DisplayName("영속성 엔터티 삭제")
    @ValueSource(ints={1})
    void testRemoveEntity(int menuCode){
        
        EntityManager manager = EntityManagerGenerator.getInstance();
        
        EntityTransaction transaction = manager.getTransaction();

        Menu foundMenu = manager.find(Menu.class, menuCode);

        transaction.begin();
        
        // remove() : 엔터티를 영속성 및 DB 에서 제거. 단, 트랜잭션 제어하지 않으면 영구 반영 안됨
        manager.remove(foundMenu);

        // remove() 반영
        manager.flush();

        Menu refoundMEnu = manager.find(Menu.class,menuCode);

        Assertions.assertNull(refoundMEnu);
    }

}
