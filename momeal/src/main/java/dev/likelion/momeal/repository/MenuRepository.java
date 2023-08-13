package dev.likelion.momeal.repository;

import dev.likelion.momeal.entity.MenuEntity;
import org.springframework.data.repository.CrudRepository;

public interface MenuRepository extends CrudRepository<MenuEntity, Long> {
}
