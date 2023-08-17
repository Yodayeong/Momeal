# Momeal_BackEnd
Mobile Meal Ticket Service for Likelion 11th Hackarthon

<br>

**Contributors**

<a href="https://github.com/LikeLionMoMeal/backend/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=LikeLionMoMeal/backend" />
</a>

<br>

<br>

**Architecture**

- ERD

  ![erd](image.assets/erd.png)

- API 명세

  ![signup](image.assets/signup.png)

  ![check](image.assets/check.png)

  ![login](image.assets/login.png)

  ![restaurant](image.assets/restaurant.png)

  ![ticket](image.assets/ticket.png)

  ![mypage](image.assets/mypage.png)

  ![kakao-ready](image.assets/kakao-ready.png)

  ![kakao-success](image.assets/kakao-success.png)

  ![kakao-fail](image.assets/kakao-fail.png)

  ![kakao-cancel](image.assets/kakao-cancel.png)

  ![create](image.assets/create.png)

  ![update](image.assets/update.png)

  ![delete](image.assets/delete.png)

  ![last](image.assets/last.png)

- MySQL Workbench로 데이터베이스 생성

  ![momeal_likelion](image.assets/momeal_likelion.png)

  - schema: momeal_likelion, user: momeal 으로 설정된 localhost-momeal 커넥션 사용

<br>

**Hardships**

#### 이미지 파일 업로드/다운로드

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


<br>

#### 스프링 배포

1. 서버 생성 & 공인 IP 생성

   - https://growingsaja.tistory.com/326
   - 생성된 IP 주소로 application.yml 등 수정하기

2. 포트포워딩

   - https://puleugo.tistory.com/13#article-4-1--1--putty-
   - 포트가 현재 8088(애플리케이션), 3306(my-sql)인데, 이 둘을 모두 외부에서 접근할 수 있도록 포트를 열어줘야함

3. File Zilla(파일 전송 시스템)로 클라우드 서버로 접속하기(새 사이트 열기)

   - https://m.blog.naver.com/myrikason/221802491577
   - 관리자 비밀번호 확인하기로 사용자, 비밀번호 확인하기 + 호스트, 포트도 작성
   - https://dev-coco.tistory.com/68
   - Intellij에서 배포 파일을 만들고 build > libs > jar파일을 File Zilla로 클라우드에 올림

4. PuTTY Configuration에서 창을 띄워서 필요한 것들 설치

   -  `ls -al`: jar 파일이 제대로 올라갔는지 확인

   <br>

   - (기본 설정)
   -  `sudo apt update`: 설치 가능한 패키지 리스트 최신화

   <br>

   - (jdk 설치)
   - `sudo apt install openjdk-11-jdk`
   - `java -version`

   <br>

   - (mysql 설치)
   - `sudo apt install mysql-server`
   - `sudo systemctl start mysql`

   <br>

   - (mysql 실행)
   -  `sudo mysql -u root -p`: mysql 실행
   - `use mysql`
   - `sudo apt install mysql-client`
   - `sudo apt update`

   <br>

   - (유저 생성 - 예시)
   - `create user 'momeal'@'%' identified by 'momeal';`
   - `grant all privileges on . To 'momeal'@'%';`
   - `flush privileges;`

   <br>

   - (데이터베이스/스키마 설치)
   - `create database [데이터베이스 이름]`
   - `use [데이터베이스 이름]`

   <br>

   - (bind address 설정)
   - `cd /etc/mysql/mysql.conf.d`
   - `sudo vi mysqld.cnf;`
   - `bind-adderess 127.0.0.0.1 을 0.0.0.0 으로 변경`
   - `:wq`로 exit

   <br>

   - (프로젝트 실행)
   - `java -jar [jar파일이름]`

