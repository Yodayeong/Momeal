package dev.likelion.momeal.controller;

import dev.likelion.momeal.dto.MenuDto;
import dev.likelion.momeal.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("menu")
public class MenuController {
    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
    private final MenuService menuService;

    public MenuController(
            @Autowired MenuService menuService
    ) {
        this.menuService = menuService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createMenu(@RequestBody MenuDto dto) {
        this.menuService.createMenu(dto);
        logger.info(dto.toString());
    }

    @GetMapping("{id}")
    public MenuDto menuDto(@PathVariable("id") int id) {
        return this.menuService.readMenu(id);
    }

    @GetMapping("")
    public List<MenuDto> readMenuAll() {
        return this.menuService.readMenuAll();
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateMenu(
            @PathVariable("id") int id,
            @RequestBody MenuDto dto
    ) {
        this.menuService.updateMenu(id, dto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteMenu(@PathVariable("id") int id) {
        this.menuService.deleteMenu(id);
    }

}
