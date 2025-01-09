package com.ohgiraffers.associationmapping.section02.onetomany;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OneToManyTest {

    @Autowired
    private OneToManyService service;

    /*
    * DML 구문을 사용할 때 @Transactional 어노테이션을 사용하면
    * 조건의 새당하는 데이터가 10개면 10번의 select 가 동작하게 된다 -> 성능상 이슈 발생
    *
    * 따라서 일단 카테고리만 조회 후 필요시 로드한다.
    * @OneToMAny(fetch= FetchType.LAZY) -> 지연 로딩, 카테고리 조회 시 연관 메뉴는 부르기 전까지 조회하지 않는 것.
    * @ManyToOne(fetch = FetchType.EAGER)
     *   1개의 엔티티를 조회할 때 1개만 조회하면 되므로
     *   즉시 로딩(이른 로딩)이 디폴트 값이다.
    * */

    @Test
    @DisplayName("1:N 연관관계 객체 그래프 탐색 조회")
    void oneToManyFind(){
        int categoryCode = 10;

        Category category = service.findCategory(categoryCode);

        System.out.println("getMenuList = " + category.getMenuList());

        Assertions.assertNotNull(category);
    }


}
