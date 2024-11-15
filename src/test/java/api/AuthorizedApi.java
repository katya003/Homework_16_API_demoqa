package api;

import data.LoginData;
import models.LoginResponseModel;
import models.LoginUserModel;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.DemoQaSpec.authUserResponse200Spec;
import static specs.DemoQaSpec.createRequestSpec;

public class AuthorizedApi {
    public static LoginResponseModel getAuthorizationCookie() {
        LoginResponseModel response;
        LoginUserModel request = new LoginUserModel();
        LoginData loginData = new LoginData();
        request.setUserName(loginData.getUserName());
        request.setPassword(loginData.getPassword());

        response = step("Залогинить пользователя", () ->
                given(createRequestSpec)
                        .body(request)
                        .when()
                        .post("/Account/v1/Login")
                        .then()
                        .spec(authUserResponse200Spec)
                        .extract().as(LoginResponseModel.class));

        return response;
    }
}
