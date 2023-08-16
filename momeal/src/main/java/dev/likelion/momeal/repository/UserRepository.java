package dev.likelion.momeal.repository;

import dev.likelion.momeal.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<UserEntity,String>{

    UserEntity findByEmail(String email);
    boolean existsByEmail (String email);
}
