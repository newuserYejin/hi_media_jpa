package com.ohgiraffers.associationmapping.section01.manytoone;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "menu_and_category")
@Table(name = "tbl_menu")
@NoArgsConstructor
@AllArgsConstructor
@Getter                     // 엔티티는 중요한 존재로 Setter 를 사용하지 않는다.
@ToString
public class Menu {

    @Id
    @Column(name = "menu_code")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int menuCode;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

    // 영속성 전이 : 특정 엔티티를 영속화 할 때, 연관관계에 있는 엔티티도 같이 영속화한다.

    // 1번 메뉴 영속 시, Category 엔터티도 같이 영속화

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_code")
    private Category category;

    @Column(name = "orderable_status")
    private String orderableStatus;

}
