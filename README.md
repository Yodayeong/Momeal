# Momeal_BackEnd
Mobile Meal Ticket Service for Likelion 11th Hackarthon

<br>

**Architecture**

- ERD

- API 명세

- MySQL Workbench로 데이터베이스 생성

  ![momeal_likelion](image.assets/momeal_likelion.png)

  - schema: momeal_likelion, user: momeal 으로 설정된 localhost-momeal 커넥션 사용

<br>

**Hardships**

- 이미지 파일 업로드/다운로드

  - multipart형으로 디비에 모두 저장해버리면, 용량이 너무 커지기 때문에
  - DB에는 사진의 경로만 String으로 저장해주면 된다.
  - 이미 올라간 사진의 경로는 불러 오면 되고, 새로 업로드 할 사진은, 지정 경로에 저장 및 DB에 파일 정보 저장 작업을 해주면 된다.
  - 로컬에서의 경우를 구현하여 보았다.

- `MenuController`

  ```java
  @RestController
  @RequestMapping("menu")
  public class MenuController {
      ...
  
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
  
  }
  ```

- `MenuService`

  ```java
  @Service
  public class MenuService {
      ...
  
      public void createMenu(String title, int price, String restaurant, MultipartFile multipartFile) throws IOException {
          String originalFileName = multipartFile.getOriginalFilename();
          String saveFileName = createSaveFileName(originalFileName);
  
          //서버에 파일 저장
          multipartFile.transferTo(new File(getFullPath(saveFileName)));
  
          //DB에 정보 저장
          String contentType = multipartFile.getContentType();
  
          this.menuDao.createMenu(title, price, restaurant, saveFileName);
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
          return "/Users/yodayeong/Desktop/momeal_img/" + saveFileName;
      }
  }
  ```

- `MenuDao`

  ```java
  @Repository
  public class MenuDao {
      ...
  
      public void createMenu(String title, int price, String restaurant, String saveFileName) {
          MenuEntity menuEntity = new MenuEntity();
          menuEntity.setTitle(title);
          menuEntity.setPrice(price);
          menuEntity.setPicture(saveFileName);
          menuEntity.setRestaurant(restaurant);
          this.menuRepository.save(menuEntity);
      }
  }
  ```

- 포스트맨 테스팅

  ![register](image.assets/register.png)

  ![file](image.assets/file.png)

  ![read](image.assets/read.png)

  ![sql](image.assets/sql.png)

  
  
  
