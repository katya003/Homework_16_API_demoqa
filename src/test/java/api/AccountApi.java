package api;

import models.GetBookListModel;
import models.LoginResponseModel;
import models.LoginUserModel;
import static helpers.extensions.LoginExtension.cookies;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.DemoQaSpec.*;

public class AccountApi {

    public static LoginResponseModel getAuthorizationCookie() {
        LoginResponseModel response;
        LoginUserModel request = new LoginUserModel(System.getProperty("loginUser"),
                System.getProperty("passwordUser"));

        response = step("Сделать запрос логина, и записать ответ", () ->
                given(createRequestSpec)
                        .body(request)

                        .when()
                        .post("/Account/v1/Login")

                        .then()
                        .spec(authUserResponse200Spec)
                       // .statusCode(200)
                        .extract().as(LoginResponseModel.class));

        return response;
    }
    public static GetBookListModel getListOfBooks() {
        GetBookListModel response;
         response = step("Получить список книг", () ->
                given(createBookStoreRequestSpec)
                        .header("Authorization", "Bearer " + cookies.getToken())
                        .queryParam("UserId", cookies.getUserId())
                        .when()
                        .get("/Account/v1/User/" + cookies.getUserId())
                        .then()
                        .spec(authUserResponse200Spec)
                        .extract().as(GetBookListModel.class));

        return response;
    }
}
