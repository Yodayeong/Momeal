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
import java.util.List;
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

    public void createMenu(String title, int price, String restaurant, String saveFileName) {
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setTitle(title);
        menuEntity.setPrice(price);
        menuEntity.setPicture(saveFileName);
        menuEntity.setRestaurant(restaurant);
        this.menuRepository.save(menuEntity);
    }

    public Iterator readMenu(String restaurant) {
        return this.menuRepository.findAllByRestaurant(restaurant).iterator();
    }

    public void updateMenu(int id, MenuDto dto) {
        Optional<MenuEntity> targetEntity = menuRepository.findById(Long.valueOf(id));
        if(targetEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        MenuEntity menuEntity = targetEntity.get();
        menuEntity.setTitle(dto.getTitle() == null ? menuEntity.getTitle() : dto.getTitle());
        menuEntity.setPrice(Integer.valueOf(dto.getPrice()) == null ? menuEntity.getPrice() : dto.getPrice());
        menuEntity.setPicture(menuEntity.getPicture());
        menuEntity.setRestaurant(dto.getRestaurant() == null ? menuEntity.getRestaurant() : dto.getRestaurant());
        this.menuRepository.save(menuEntity);
    }

    public void updateMenuPicture(int id, String saveFileName) {
        Optional<MenuEntity> targetEntity = menuRepository.findById(Long.valueOf(id));
        if(targetEntity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        MenuEntity menuEntity = targetEntity.get();
        //menuEntity.setTitle(dto.getTitle() == null ? menuEntity.getTitle() : dto.getTitle());
        //menuEntity.setPrice(Integer.valueOf(dto.getPrice()) == null ? menuEntity.getPrice() : dto.getPrice());
        menuEntity.setPicture(saveFileName);
        //menuEntity.setRestaurant(dto.getRestaurant() == null ? menuEntity.getRestaurant() : dto.getRestaurant());
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
