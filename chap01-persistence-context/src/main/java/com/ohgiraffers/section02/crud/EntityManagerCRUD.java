package com.ohgiraffers.section02.crud;

import com.ohgiraffers.section01.entitymanager.EntityManagerGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class EntityManagerCRUD {

    private EntityManager manager;

    public EntityManager getManagerInstance(){
        return manager;
    }

    public Menu findMenuByMenuCode(int menuCode) {

        manager = EntityManagerGenerator.getInstance();

        return manager.find( Menu.class ,menuCode);
    }

    public Long saveAndReturnCount(Menu newMenu) {
        manager = EntityManagerGenerator.getInstance();

        EntityTransaction transaction = manager.getTransaction();

        transaction.begin();

        manager.persist(newMenu);
        manager.flush();                // DB에 반영은 하였으나 저장은 안한 상태

        transaction.commit();

        return getCount(manager);
    }

    private Long getCount(EntityManager manager) {
        Long count = manager.createQuery("SELECT COUNT(*) FROM section02Menu", Long.class).getSingleResult();

        System.out.println("count = " + count);

        return count;
    }

    public Menu modifyMenuName(int code, String name) {

        Menu foundMenu = findMenuByMenuCode(code);
        System.out.println("foundMenu = " + foundMenu);

//        manager = EntityManagerGenerator.getInstance();

        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();

        foundMenu.setMenuName(name);

        transaction.commit();

        return foundMenu;
    }

    public Long removeAndReturnCount(int code) {

        Menu foundMenu = findMenuByMenuCode(code);

        EntityTransaction transaction = manager.getTransaction();

        transaction.begin();

        manager.remove(foundMenu);

        transaction.commit();

        return getCount(manager);
    }
}
