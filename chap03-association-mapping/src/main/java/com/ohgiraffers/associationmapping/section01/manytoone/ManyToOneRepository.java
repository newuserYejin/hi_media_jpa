package com.ohgiraffers.associationmapping.section01.manytoone;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ManyToOneRepository {

    @PersistenceContext
    private EntityManager manager;


    public Menu findMenu(int menuCode) {

        return manager.find(Menu.class, menuCode);
    }

    // 내가 생각한 방식
//    public String findCategoryName(int menuCode) {
//        Menu foundMenu = manager.find(Menu.class,menuCode);
//
//        return foundMenu.getCategory().getCategoryName();
//    }

    // JPQL : 엔티티를 통한 상세 조회이기 때문에 from 에 엔티티명 사용, 조회 시 사용
    public String findCategoryName(int menuCode) {

        String jpql = "SELECT c.categoryName " +
                        "FROM menu_and_category m " +
                        "JOIN m.category c " +
                        "WHERE m.menuCode = :menuCode";
        return manager.createQuery(jpql,String.class)
                .setParameter("menuCode", menuCode)
                .getSingleResult();          // 결과가 1개
    }

    public void regist(Menu menu) {

        manager.persist(menu);

    }
}
