package com.ohgiraffers.section01.entitymanager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class EntityManagerGenerator {

    // manager 생성
    public static EntityManager getInstance(){

        // 공장 정보 가져오기
        EntityManagerFactory factory = EntityManagerFactoryGenerator.getInstance();

         return factory.createEntityManager();
    }

}
