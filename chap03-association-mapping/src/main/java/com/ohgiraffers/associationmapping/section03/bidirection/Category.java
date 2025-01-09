package com.ohgiraffers.associationmapping.section03.bidirection;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity(name = "bi_category")
@Table(name = "tbl_category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
//@ToString
public class Category {

    @Id
    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "ref_category_code")
    private Integer refCategoryCode;

    // 객체에 참조는 둘인데 외래키는 1개인 상황에서 두 연관관계중 하나를 정해 테이블의 외래키를 관리한다. -> 연관관계의 주인(Owner)
    // 주인이 아닌 쪽 -> mappedBy 를 사용하여 연관된 필드를 가르킨다.

    @OneToMany(mappedBy = "category")
    private List<Menu> menuList;

    public Category(int categoryCode, String categoryName, Integer refCategoryCode) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.refCategoryCode = refCategoryCode;
    }

}
