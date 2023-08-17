package dev.likelion.momeal.dao;

import dev.likelion.momeal.repository.CartRepository;
import dev.likelion.momeal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CartDao {
    private static final Logger logger = LoggerFactory.getLogger(CartDao.class);
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public void createCart(int price, int quantity, String email) {
        CartEntity cartEntity = new CartEntity();
        cartEntity.setPrice(price);
        cartEntity.setQuantity(quantity);
        cartEntity.setUserEntity(userRepository.findByEmail(email));

        cartRepository.save(cartEntity);
    }
}
