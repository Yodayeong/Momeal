package dev.likelion.momeal.controller;

import dev.likelion.momeal.dto.MenuDto;
import dev.likelion.momeal.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PostMapping(
            value = "/register",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public void createMenu(
            @RequestParam("title") String title,
            @RequestParam("price") int price,
            @RequestParam("restaurant") String restaurant,
            @RequestParam("picture") MultipartFile multipartFile
    ) throws IOException {

        logger.info("title: " + title);
        logger.info("price: " + price);
        logger.info("restaurant: " + restaurant);
        logger.info("picture: " + multipartFile.getOriginalFilename());

        this.menuService.createMenu(title, price, restaurant, multipartFile);
    }

    @GetMapping("{restaurant}/menu")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<MenuDto> readMenu(
            @PathVariable("restaurant") String restaurant
    ) {

        return this.menuService.readMenu(restaurant);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateMenu(
            @PathVariable("id") int id,
            @RequestBody MenuDto dto
    ) {
        this.menuService.updateMenu(id, dto);
    }

    @PutMapping(
            value = "/{id}/picture",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateMenu(
            @PathVariable("id") int id,
            @RequestParam("picture") MultipartFile multipartFile
    ) throws IOException {
        logger.info("picture: " + multipartFile.getOriginalFilename());

        this.menuService.updateMenuPicture(id, multipartFile);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteMenu(@PathVariable("id") int id) {
        this.menuService.deleteMenu(id);
    }

}
