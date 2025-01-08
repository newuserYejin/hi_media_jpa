package com.ohgiraffers.mapping.section01.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

// mybatis 에서는 @Mapper 가 DB 접근 계층
// JPA 에서는 @Repository 가 해당 역할
@Repository
public class MemberRepository {

    /*
     * entity 매니저를 주입 받아 영속성 컨테이너가 Member를 관리 가능하게 한다.
     * */
    @PersistenceContext
    private EntityManager manager;

    public void save(Member member) {

        // 영속화 등록
        manager.persist(member);

    }

}
