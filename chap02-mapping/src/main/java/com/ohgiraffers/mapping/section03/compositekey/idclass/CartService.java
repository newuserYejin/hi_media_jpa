package com.ohgiraffers.mapping.section03.compositekey.idclass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    @Autowired
    private CartRepository repository;

    @Transactional
    public void addItemToCart(CartDTO cart) {

        Cart cartEntity = new Cart(
                cart.getCartOwner(),
                cart.getAddBookNo(),
                cart.getQuantity()
        );

        repository.save(cartEntity);

    }
}
