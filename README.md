## MyMOO Movie Application

- Spring Boot
- Thymeleaf
- Maven
- Spring DataJPA
- Select2 API - For multiple selection

--------------------

### Roller ve İşlevler

###### anonymous user

- Normal user olarak kayıt olabilme
- Kullanıcı adı ve şifre ile giriş, Google hesabı ile giriş yapabilme
- Film arama
  - Kategori içerisinde arama
  - Film adı veya oyuncu adı ile arama
  - Film adı veya yıla göre sıralama

###### normal user (ROLE_USER)

- Film ekleme
- Film düzenleme (eğer kendi eklediği bir film ise)
- Herhangi bir filmi favori filmlerine ekleme/çıkarma
- Oyuncu ekleme

###### admin user (ROLE_ADMIN)

- Herhangi bir filmi silebilir ya da düzenleyebilir
- Kategori ekleme, silme, düzenleme
- /actuator erişim

----------------

### Running Application

###### Setting Up Database

- schema adı: movie_db

- moviedump.sql dosyasını kullanarak MySQL tablo ve verileri ekleyebilirsiniz.

- DB instance application.properties dosyasında;

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/movie_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=admin
```

olarak belirtilmiştir. Bu bilgileri kendinize göre düzenleyiniz.

- Projeyi IDE'nize import edin ve çalıştırın:

###### Eclipse:

> File -> Import -> Maven -> Existing Maven Project -> Browse -> /movieapp

###### Apache NetBeans

> File -> Open  Project -> Browse -> /movieapp

###### Command Line (Windows)

Build: `/movieapp> mvnw intall`

Run: `/movieapp/target> java -jar movieapp-0.0.1-SNAPSHOT.jar`

veya `/movieapp> mvnw spring-boot:runm`

---------------------------

#### NOTLAR

- Dump.sql içinde yer alan örnek normal ve admin user:

  - ```
    Kullanıcı Adı: user
    Parola: 1
    ---------
    Kullanıcı Adı: admin
    Parola: 1
    ```

  - Uygulama içi kullanıcı kaydı sadece normal kullanıcılar için vardır. Bu nedenle admin kullanıcı için yukarıdaki kullanıcıyı kullabilirsiniz veya veritabanında user_role tablosuna 2 nolu role_id ekleyin.













