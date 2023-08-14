package dev.likelion.momeal.repository;

import dev.likelion.momeal.entity.MenuEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MenuRepository extends CrudRepository<MenuEntity, Long> {
    List<MenuEntity> findAllByRestaurant(String restaurant);
}
