# MyMOO Movie Application
## Included Starters
- Spring Boot DevTools
- Spring Boot Actuator
- Spring Web
- Spring Data JPA
- MySQL Driver
- Spring Security
- Oauth2 Client
- Thymeleaf
### CSS-JS
- Select2 API (for multiple selection)
- Bootstrap 4
- Custom CSS
--------------------
### ER Diagram
![image](https://drive.google.com/uc?export=view&id=10CfeOPvg325IwRcDllUtBvJmRE8pquc9)

## Roles and Functions
###### anonymous user
- Register as a normal user
- Login with username and password, login with Google account
- Search
  - Search in category
  - Search by title or actor
- Sort
  - Sort by title or year
###### normal user (ROLE_USER)
- Add new movie
- Edit movie (if added by that user)
- Add/Remove to/from favorites any movie.
- Add actor
###### admin user (ROLE_ADMIN)
- Update/Delete any movie
- Add/Delete/Update categories
- Access to localhost:8080/actuator
----------------
## Running Application
###### Setting Up Database
- Schema name: movie_db
- Use sql/moviedump.sql file to create tables and insert data.
- DB instance configuration in: `application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/movie_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=admin
```
Edit prop file for your purpose.

###### Import Project To IDE
- Eclipse:
> File -> Import -> Maven -> Existing Maven Project -> Browse -> /movieapp
- Apache NetBeans
> File -> Open  Project -> Browse -> /movieapp
###### Run From Command Line (Windows)
Build: `/movieapp> mvnw intall`
Run: `/movieapp/target> java -jar movieapp-0.0.1-SNAPSHOT.jar`
or `/movieapp> mvnw spring-boot:run`

---------------------------

#### Extra Notes
- Example users in moviedump.sql:
  - ```
    ROLE_USER
    Username: user
    Password: 1
    ---------
    ROLE_USER, ROLE_ADMIN
    Username: admin
    Password: 1
    ```













