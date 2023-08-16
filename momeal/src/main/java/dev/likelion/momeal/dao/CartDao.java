package dev.likelion.momeal.dao;

import dev.likelion.momeal.entity.CartEntity;
import dev.likelion.momeal.entity.MenuEntity;
import dev.likelion.momeal.repository.CartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CartDao {
    private static final Logger logger = LoggerFactory.getLogger(CartDao.class);
    private final CartRepository cartRepository;

    public CartDao(
            @Autowired CartRepository cartRepository
    ) {
        this.cartRepository = cartRepository;
    }

    public void createCart() {

    }
}
