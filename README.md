![](src/main/resources/img/logo.png)

# Информация

## Авторы:<br>
Максим Сыров ([CatUnderGlue](https://github.com/CatUnderGlue))<br>
Ильяс Кучукбаев ([Ilyas344](https://github.com/Ilyas344))<br>
Исакова Мария ([profmi2022](https://github.com/profmi2022))<br>
Ильдар Губайдуллин ([MexxMo](https://github.com/MexxMo))<br>
Андрей Попов ([zalex14](https://github.com/zalex14))<br>

## Проект: Наследник Ebay и Avito <br>
## Старт проекта: 22.05.2023<br>

## Описание: 
"eBito" - это онлайн-платформа, объединяющая международный маркетплейс eBay и российский
Avito. С помощью eBito пользователи могут покупать и продавать товары и услуги во всем
мире, включая Россию и другие страны, где присутствует eBay. Платформа предлагает широкий
выбор категорий товаров: электроника, мода, спорт, авто и многое другое. eBito обеспечивает
безопасность сделок и защиту покупателей и продавцов, а также предоставляет удобный и
простой интерфейс для использования. С eBito покупки и продажи становятся быстрыми,
удобными и доступными для всех.<br>

# Запуск приложения
+ Перед запуском приложения _обязательно_ добавьте эти переменные в параметры запуска:
  * db.url = ссылка на подключение к вашей бд
  * db.user = ваш пользователь бд
  * db.password = ваш пароль от бд
+ Загрузить контейнер с фронтэнд частью с помощью команды:<br>
  `docker pull ghcr.io/bizinmitya/front-react-avito:v1.17`
+ Для запуска контейнера использовать команду:<br>
+ `docker run --rm -p 3000:3000 ghcr.io/bizinmitya/front-react-avito:v1.17`
+ Можно запускать главный класс приложения.

# Стэк технологий
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white "Java 11")
![Maven](https://img.shields.io/badge/Maven-green.svg?style=for-the-badge&logo=mockito&logoColor=white "Maven")
![Spring](https://img.shields.io/badge/Spring-blueviolet.svg?style=for-the-badge&logo=spring&logoColor=white "Spring")
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![GitHub](https://img.shields.io/badge/git-%23121011.svg?style=for-the-badge&logo=github&logoColor=white "Git")
+ ЯП: *Java 11*
+ Автоматизация сборки: *Maven*
+ Фреймворк: *Spring*
+ База данных: *PostgreSQL*
+ Контроль версий: *Git*

<details>
  <summary>Зависимости</summary>

       <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!-- Security -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <!-- Базы данных -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>
        <!-- Swagger -->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-ui</artifactId>
            <version>1.6.15</version>
        </dependency>
        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <!-- Test -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!-- Mapping -->
        <dependency>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct</artifactId>
            <version>1.5.5.Final</version>
        </dependency>
        <dependency>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct-processor</artifactId>
            <version>1.5.5.Final</version>
        </dependency>
    </dependencies>
</details>

# Классы и связи
```mermaid
classDiagram
    class Ads {
        -Integer id
        -String title
        -String description
        -int price
        -User author;
        -Image image;
    }

    class Comment {
        -private Integer id
        -Long createdAt
        -String text
        -Ads ads
        -User author
    }

    class Image {
        -String id
    }

    class User {
        -Integer id
        -String firstName
        -String lastName
        -String email
        -String password
        -String phone
        -Image image
        -Role role
        -List<Ads> ads
    }

    class Role {
        <<enumeration>>
        +USER
        +ADMIN
    }

    class GrantedAuthority {
        <<Interface>>

    }

    class LoginReq {
        -String password
        -String username
    }

    class RegisterReq {
        -String username
        -String password
        -String firstName
        -String lastName
        -String phone
        -Role role
    }

    class NewPasswordDto {
        -String currentPassword;
        -String newPassword;
    }

    class AdsDto {
        -Integer author
        -String image
        -Integer pk
        -Integer price
        -String title
    }

    class CommentDto {
        -Integer author
        -String authorImage
        -String authorFirstName
        -Long createdAt
        -Integer pk
        -String text
    }

    class CreateAdsDto {
        -String description
        -Integer price
        -String title
    }

    class CreateCommentDto {
        -String text
    }

    class FullAdsDto {
        -Integer pk
        -String authorFirstName
        -String authorLastName
        -String description
        -String email
        -String image
        -String phone
        -Integer price
        -String title
    }

    class ResponseWrapperAdsDto {
        -Integer count
        -List<AdsDto> results
    }

    class ResponseWrapperCommentDto {
        -Integer count
        -List<CommentDto> results
    }

Role <|-- GrantedAuthority
    
Ads -- User
Ads -- Image

Comment -- Ads
Comment -- User

User -- Image
User -- Ads
Role o-- User

Ads ..|> AdsDto
Ads --|> FullAdsDto
Comment ..|> CommentDto
User ..|> UserDto
Image ..|> UserDto
  
AdsDto -- ResponseWrapperAdsDto
CommentDto -- ResponseWrapperCommentDto

AdsDto -- CreateAdsDto
FullAdsDto -- AdsDto
CommentDto -- CreateCommentDto

UserDto -- NewPasswordDto
UserDto -- LoginReq
UserDto -- RegisterReq

```
