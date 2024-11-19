package api;

import models.IsbnModel;
import models.AddBookListRequestModel;

import java.util.List;

//import static data.AuthorizedData.USER_ID;
//import static data.AuthorizedData.USER_TOKEN;
import static helpers.extensions.LoginExtension.cookies;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.DemoQaSpec.*;

public class BookStoreApi {
    public static void deleteAllBooksInCart() {
        step("Удалить книги из корзины", () -> {
            given(createRequestSpec)
                    .header("Authorization", "Bearer " + cookies.getToken())
                    .queryParam("UserId", cookies.getUserId())
                    .when()
                    .delete("/BookStore/v1/Books")
                    .then()
                    .spec(deleteBook204Spec);
        });

    }

    public static void addBookToList(String isbn) {

        IsbnModel isbnModel = new IsbnModel(isbn);
        AddBookListRequestModel request = new AddBookListRequestModel(cookies.getUserId(), List.of(isbnModel));
        /*isbnModel.setIsbn(isbn);
        request.setUserId(USER_ID);
        request.setCollectionOfIsbns(List.of(isbnModel));*/


        step("Добавить книгу в корзину", () -> {
            given(createRequestSpec)
                    .header("Authorization", "Bearer " + cookies.getToken())
                    .body(request)
                    .when()
                    .post("/BookStore/v1/Books")
                    .then()
                    .spec(successfulResponse201Spec);
        });

    }
}

