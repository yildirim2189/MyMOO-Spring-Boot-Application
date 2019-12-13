# MyMOO Movie Application
## Project Structure
![image](https://drive.google.com/uc?export=view&id=1VEhSsayZ1BR7j0w2A8Kc2wGpXLKu0Hth)
![image](https://drive.google.com/uc?export=view&id=1INuxAhpwQjaXOJliKrbn9j8J2dckDOU3)

---------------
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
---------
### ER Diagram
- A movie can have many categories, a category can have many movies (Many to Many) 
- A movie can have many actors, an actor can act in many movies (Many to Many)
- A user can have many favorite movies, a movie can be favorited by many users (Many to Many)
- User with many roles, role with many users (Many to Many)
- A movie can be added by only one user (One to One)

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
- Edit prop file for your purpose.
- Default server port:8080

###### Import Project To IDE
- Eclipse:
> File -> Import -> Maven -> Existing Maven Project -> Browse -> /movieapp
- Apache NetBeans
> File -> Open  Project -> Browse -> /movieapp
###### Run From Command Line (Windows)
- Build: `/movieapp> mvnw intall`
- Run: `/movieapp/target> java -jar movieapp-0.0.1-SNAPSHOT.jar`
- or Run: `/movieapp> mvnw spring-boot:run`

---------------------------

### Extra Notes
- Example users in moviedump.sql:
```
    ROLE_USER
    Username: user
    Password: 1
    ---------
    ROLE_USER, ROLE_ADMIN
    Username: admin
    Password: 1
```
# Screenshots
## Home Page
![image](https://drive.google.com/uc?export=view&id=1NC2vqAH-CViNzalixHZQOexCa_U2oHJn)
## Movie Details
![image](https://drive.google.com/uc?export=view&id=158i40aYL6P2MDnehOF6HS_kQlrvhZ8KA)
## Favorite Movies
![image](https://drive.google.com/uc?export=view&id=1cx_U1MHkZys-Mykl2Fr91L7-fbgqFbGB)
## Add/Edit Movie
![image](https://drive.google.com/uc?export=view&id=1aFCf5ZexjyQ9XYDXxQVH1uxZyg7mI3DD)












