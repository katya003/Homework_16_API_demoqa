package tests;

import api.AccountApi;
import api.BookStoreApi;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import helpers.extensions.WithLogin;
import models.GetBookListModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import page.ProfilePage;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("API")

@DisplayName("Тесты книжного магазина Book Store на сайте demoqa.com")
public class DemoTests extends TestBase {
    @Test
    @WithLogin
    @DisplayName("Проверка успешного удаления книги из списка")
    void successfulDeleteBookTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Удалить все книги из корзины", () ->
                BookStoreApi.deleteAllBooksInCart());

        step("Добавить книгу в корзину", () ->
                BookStoreApi.addBookToList("9781449325862"));

        step("Удалить добавленную книгу", () -> {
            ProfilePage.openPage();
            ProfilePage.deleteOneBook();
        });

        step("Проверить удаление книги через UI", () -> {
            ProfilePage.openPage();
            ProfilePage.checkDeleteBookWithUI();
        });

        step("Получить список книг в корзине через API", () -> {
            GetBookListModel response = AccountApi.getListOfBooks();
            assertThat(response.getBooks()).isNotNull();
        });

        step("Проверить удаление книги через API", () -> {
            GetBookListModel response = AccountApi.getListOfBooks();
            assertThat(response.getBooks()).isEmpty();
        });
    }
}



