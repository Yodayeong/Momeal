package dev.likelion.momeal.repository;

import dev.likelion.momeal.entity.OrderEntity;
import dev.likelion.momeal.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {

    @Query("SELECT o FROM OrderEntity o WHERE o.userEntity = :userEntity")
    OrderEntity findByEmail(String email);


}
