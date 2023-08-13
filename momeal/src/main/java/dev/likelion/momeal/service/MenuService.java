package dev.likelion.momeal.service;

import dev.likelion.momeal.dao.MenuDao;
import dev.likelion.momeal.dto.MenuDto;
import dev.likelion.momeal.entity.MenuEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class MenuService {
    private static final Logger logger = LoggerFactory.getLogger(MenuService.class);
    private final MenuDao menuDao;

    public MenuService(
            @Autowired MenuDao menuDao
    ) {
        this.menuDao = menuDao;
    }

    public void createMenu(MenuDto menuDto) {
        this.menuDao.createMenu(menuDto);
    }

    public MenuDto readMenu(int id) {
        MenuEntity menuEntity = this.menuDao.readMenu(id);
        return new MenuDto(
                menuEntity.getId(),
                menuEntity.getTitle(),
                menuEntity.getPrice(),
                menuEntity.getPicture(),
                menuEntity.getAmount(),
                menuEntity.getRestaurant()
        );
    }

    public List<MenuDto> readMenuAll() {
        Iterator<MenuEntity> iterator = this.menuDao.readMenuAll();
        List<MenuDto> menuDtoList = new ArrayList<>();

        while(iterator.hasNext()) {
            MenuEntity menuEntity = iterator.next();
            menuDtoList.add(new MenuDto(
                    menuEntity.getId(),
                    menuEntity.getTitle(),
                    menuEntity.getPrice(),
                    menuEntity.getPicture(),
                    menuEntity.getAmount(),
                    menuEntity.getRestaurant()
            ));
        }
        return menuDtoList;
    }

    public void updateMenu(int id, MenuDto menuDto) {
        this.menuDao.updateMenu(id, menuDto);
    }

    public void deleteMenu(int id) {
        this.menuDao.deleteMenu(id);
    }
}
