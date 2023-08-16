package dev.likelion.momeal.repository;

import dev.likelion.momeal.entity.OrderEntity;
import dev.likelion.momeal.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {

    @Query("SELECT o FROM OrderEntity o WHERE o.userEntity.email = :email")
    List<OrderEntity> findByEmail(@Param("email") String email);

}
