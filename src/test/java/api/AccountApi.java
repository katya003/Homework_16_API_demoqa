package api;

import models.GetBookListModel;

import static data.AuthorizedData.USER_ID;
import static data.AuthorizedData.USER_TOKEN;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.DemoQaSpec.authUserResponse200Spec;
import static specs.DemoQaSpec.createBookStoreRequestSpec;

public class AccountApi {
    public static GetBookListModel getListOfBooks() {
        GetBookListModel response = step("Получить список книг", () ->
                given(createBookStoreRequestSpec)
                        .header("Authorization", "Bearer " + USER_TOKEN)
                        .queryParam("UserId", USER_ID)
                        .when()
                        .get("/Account/v1/User/" + USER_ID)
                        .then()
                        .spec(authUserResponse200Spec)
                        .extract().as(GetBookListModel.class));

        return response;
    }
}
