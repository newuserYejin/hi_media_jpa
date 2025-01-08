package com.ohgiraffers.mapping.section03.compositekey.idclass;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CartDTO {

    private int cartOwner;
    private int addBookNo;
    private int quantity;

}
