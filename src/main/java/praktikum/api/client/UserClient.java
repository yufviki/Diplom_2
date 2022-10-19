package praktikum.api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import praktikum.api.util.User;
import praktikum.api.util.UserAuthorization;
import praktikum.api.util.UserInformationUpdate;
import static io.restassured.RestAssured.given;

public class UserClient {
    private final String REGISTER = "/auth/register";
    private final String LOGIN = "/auth/login";
    private final String USER = "/auth/user";

    @Step("Send POST request for user registration: /api/auth/register")
    public Response registration(User user) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(REGISTER);
    }

    @Step("Send POST request for user authorization: /api/auth/login")
    public Response authorization(UserAuthorization userAuthorization) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(userAuthorization)
                .when()
                .post(LOGIN);
    }

    @Step("Send PATCH request to update user information with authorization: /api/auth/user")
    public Response updateUserInformationWithAuthorization(UserInformationUpdate userInformationUpdate, String token) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .and()
                .body(userInformationUpdate)
                .when()
                .patch(USER);
    }

    @Step("Send PATCH request to update user information without authorization: /api/auth/user")
    public Response updateUserInformationWithoutAuthorization(UserInformationUpdate userInformationUpdate) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(userInformationUpdate)
                .when()
                .patch(USER);
    }

    @Step("Send DELETE request to delete a user: /api/auth/user")
    public Response deleteUser(String token) {
        return given()
                .header("Authorization", token)
                .when()
                .delete(USER);
    }
}
