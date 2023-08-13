package dev.likelion.momeal.dao;

import dev.likelion.momeal.dto.MenuDto;
import dev.likelion.momeal.entity.MenuEntity;
import dev.likelion.momeal.repository.MenuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Iterator;
import java.util.Optional;

@Repository
public class MenuDao {
    private static final Logger logger = LoggerFactory.getLogger(MenuDao.class);
    private final MenuRepository menuRepository;

    public MenuDao(
            @Autowired MenuRepository menuRepository
    ) {
        this.menuRepository = menuRepository;
    }

    public void createMenu(MenuDto dto) {
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setTitle(dto.getTitle());
        menuEntity.setPrice(dto.getPrice());
        menuEntity.setPicture(dto.getPicture());
        menuEntity.setAmount(dto.getAmount());
        menuEntity.setRestaurant(dto.getRestaurant());
        this.menuRepository.save(menuEntity);
    }

    public MenuEntity readMenu(int id) {
        Optional<MenuEntity> menuEntity = this.menuRepository.findById((long) id);
        if(menuEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return menuEntity.get();
    }

    public Iterator<MenuEntity> readMenuAll() {
        return this.menuRepository.findAll().iterator();
    }

    public void updateMenu(int id, MenuDto dto) {
        Optional<MenuEntity> targetEntity = menuRepository.findById(Long.valueOf(id));
        if(targetEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        MenuEntity menuEntity = targetEntity.get();
        menuEntity.setTitle(dto.getTitle() == null ? menuEntity.getTitle() : dto.getTitle());
        menuEntity.setPrice(Integer.valueOf(dto.getPrice()) == null ? menuEntity.getPrice() : dto.getPrice());
        menuEntity.setPicture(dto.getPicture() == null ? menuEntity.getPicture() : dto.getPicture());
        menuEntity.setAmount(Integer.valueOf(dto.getAmount()) == null ? menuEntity.getAmount() : dto.getAmount());
        menuEntity.setRestaurant(dto.getRestaurant() == null ? menuEntity.getRestaurant() : dto.getRestaurant());
        this.menuRepository.save(menuEntity);
    }

    public void deleteMenu(int id) {
        Optional<MenuEntity> targetEntity = this.menuRepository.findById((long) id);
        if(targetEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        this.menuRepository.delete(targetEntity.get());
    }
}
