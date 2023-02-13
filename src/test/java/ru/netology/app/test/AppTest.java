package ru.netology.app.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.app.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.app.data.DataGenerator.Registration.getUser;
import static ru.netology.app.data.DataGenerator.generateLogin;
import static ru.netology.app.data.DataGenerator.generatePassword;

public class AppTest {

    @BeforeEach
    void openBrowser() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSuccessfulLoginRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id = 'login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id = 'password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id = 'action-login']").click();
        $("h2")
                .shouldHave(Condition.exactText("Личный кабинет"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldGetErrorNotRegisteredActiveUser() {
        var notRegisteredUser = getUser("active");
        $("[data-test-id = 'login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id = 'password'] input").setValue(notRegisteredUser.getPassword());
        $("[data-test-id = 'action-login']").click();
        $("[data-test-id = 'error-notification'] .notification__content")
                .shouldHave(Condition.text("Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldGetErrorBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $("[data-test-id = 'login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id = 'password'] input").setValue(blockedUser.getPassword());
        $("[data-test-id = 'action-login']").click();
        $("[data-test-id = 'error-notification'] .notification__content")
                .shouldHave(Condition.text("Пользователь заблокирован"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldGetErrorWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = generateLogin();
        $("[data-test-id = 'login'] input").setValue(wrongLogin);
        $("[data-test-id = 'password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id = 'action-login']").click();
        $("[data-test-id = 'error-notification'] .notification__content")
                .shouldHave(Condition.text("Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }

    @Test
    void shouldGetErrorWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = generatePassword();
        $("[data-test-id = 'login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id = 'password'] input").setValue(wrongPassword);
        $("[data-test-id = 'action-login']").click();
        $("[data-test-id = 'error-notification'] .notification__content")
                .shouldHave(Condition.text("Неверно указан логин или пароль"))
                .shouldBe(Condition.visible);
    }

}
