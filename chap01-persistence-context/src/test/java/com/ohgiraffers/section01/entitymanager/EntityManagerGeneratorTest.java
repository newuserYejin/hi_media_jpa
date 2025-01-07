package com.ohgiraffers.section01.entitymanager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EntityManagerGeneratorTest {

    // persistence Context 이해하기

    // 엔티티 매니저 팩토리 : 엔티티 매니저를 생성할 수 있는 기능을 제공하는 클래스

    // application 당 EntityManagerFactory 1개

    @Test
    @DisplayName("엔티티 매니저 팩토리 생성 확인하기")
    void testCreateFactory(){

        EntityManagerFactory factory1 = EntityManagerFactoryGenerator.getInstance();

        EntityManagerFactory factory2 =EntityManagerFactoryGenerator.getInstance();

        System.out.println("factory1 = " + factory1.hashCode());
        System.out.println("factory2 = " + factory2.hashCode());

        // 팩토리 생성 확인
        Assertions.assertNotNull(factory1);
        Assertions.assertNotNull(factory2);
        
        // 둘이 같은지 비교하여 싱글톤 맞는지 확인
        Assertions.assertEquals(factory1,factory2);

    }

    // 앤티티 매니저 : 엔티티를 저장하는 메모리 상의 DB를 관리하는 인스턴스

    @Test
    @DisplayName("엔티티 매니저 생성 확인")
    void testCreateManager(){

        EntityManager manager1 = EntityManagerGenerator.getInstance();
        EntityManager manager2 = EntityManagerGenerator.getInstance();

        System.out.println("manager1.hashCode() = " + manager1.hashCode());
        System.out.println("manager2.hashCode() = " + manager2.hashCode());

        Assertions.assertNotNull(manager1);
        Assertions.assertNotNull(manager2);

        Assertions.assertNotEquals(manager1,manager2);
    }

}
