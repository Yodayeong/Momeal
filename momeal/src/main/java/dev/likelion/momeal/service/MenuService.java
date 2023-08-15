package dev.likelion.momeal.service;

import dev.likelion.momeal.dao.MenuDao;
import dev.likelion.momeal.dto.MenuDto;
import dev.likelion.momeal.entity.MenuEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
public class MenuService {
    private static final Logger logger = LoggerFactory.getLogger(MenuService.class);
    private final MenuDao menuDao;

    public MenuService(
            @Autowired MenuDao menuDao
    ) {
        this.menuDao = menuDao;
    }

    public void createMenu(MenuDto menuDto, MultipartFile multipartFile) throws IOException {
        String originalFileName = multipartFile.getOriginalFilename();
        String saveFileName = createSaveFileName(originalFileName);

        //서버에 파일 저장
        multipartFile.transferTo(new File(getFullPath(saveFileName)));

        //DB에 정보 저장
        String contentType = multipartFile.getContentType();

        this.menuDao.createMenu(menuDto, saveFileName);
    }

    //파일 저장 이름 만들기
    //사용자들이 올리는 파일 이름이 같을 수 있으므로, 자체적으로 랜덤 이름을 만들어 사용
    private String createSaveFileName(String originalFileName) {
        String ext = extractExt(originalFileName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFileName) {
        int pos = originalFileName.lastIndexOf(".");
        return originalFileName.substring(pos + 1);
    }

    //fullPath 만들기
    private String getFullPath(String saveFileName) {
        return "/Users/yodayeong/Desktop/momeal_img" + saveFileName;
    }

    public List<MenuDto> readMenu(String restaurant) {
        Iterator<MenuEntity> iterator = this.menuDao.readMenu(restaurant);
        List<MenuDto> menuDtoList = new ArrayList<>();

        while(iterator.hasNext()) {
            MenuEntity menuEntity = iterator.next();
            menuDtoList.add(new MenuDto(
                    menuEntity.getMenuId(),
                    menuEntity.getTitle(),
                    menuEntity.getPrice(),
                    menuEntity.getPicture(),
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
