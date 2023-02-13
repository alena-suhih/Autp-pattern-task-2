package ru.netology.app.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Data;
import lombok.Value;
import java.util.Locale;
import static io.restassured.RestAssured.given;

@Data
public class DataGenerator {
    private DataGenerator(){
    }

    private static final Faker faker = new Faker(new Locale("en"));

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static void SendRequest(RegistrationDTO user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

        public static String generateLogin() {
            var login = faker.name().username();
            return login;
        }

        public static String generatePassword() {
            var password = faker.internet().password();
            return password;
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationDTO getUser(String status) {
            var user = new RegistrationDTO(generateLogin(), generatePassword(), status);
            return user;
        }

        public static RegistrationDTO getRegisteredUser(String status) {
            var registeredUser = getUser(status);
            System.out.println("registered user" + registeredUser);
            SendRequest(registeredUser);
            return registeredUser;
        }
    }

    @Value
    public static class RegistrationDTO {
        String login;
        String password;
        String status;
    }
}
