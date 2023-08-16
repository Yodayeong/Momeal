package dev.likelion.momeal.repository;

import dev.likelion.momeal.entity.OrderEntity;
import dev.likelion.momeal.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity,Long> {

    OrderEntity findByEmail(String email);

}
