package dev.likelion.momeal.dao;

import dev.likelion.momeal.entity.CartEntity;
import dev.likelion.momeal.entity.MenuEntity;
import dev.likelion.momeal.entity.UserEntity;
import dev.likelion.momeal.repository.CartRepository;
import dev.likelion.momeal.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CartDao {
    private static final Logger logger = LoggerFactory.getLogger(CartDao.class);
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public CartDao(
            @Autowired CartRepository cartRepository,
            @Autowired UserRepository userRepository
    ) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    public void createCart(int price, int quantity, String email) {
        UserEntity user = userRepository.findByEmail(email);

        CartEntity cartEntity = new CartEntity();
        cartEntity.setPrice(price);
        cartEntity.setQuantity(quantity);
        cartEntity.setUserEntity(user);
    }
}
