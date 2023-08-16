package dev.likelion.momeal.repository;

import dev.likelion.momeal.entity.CartEntity;
import dev.likelion.momeal.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<CartEntity, Long> {
    UserEntity findByEmail(String email);
}
