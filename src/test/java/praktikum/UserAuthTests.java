package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.api.client.UserClient;
import praktikum.api.util.User;
import praktikum.api.util.UserAuthorization;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class UserAuthTests extends TestBase {
    User user;
    UserAuthorization userAuthorization;
    UserClient userClient;
    private String token;

    @Before
    public void setUpUserClient() {
        userClient = new UserClient();

        user = User.getRandomRegisterUserAllArgs();
        token = userClient.registration(user)
                .then()
                .extract()
                .path("accessToken");
    }

    @Test
    @DisplayName("Success User Authorization")
    @Description("The test verifies the successful authorization of a user: 200 OK")
    public void successUserAuthorizationTest() {
        userAuthorization = UserAuthorization.getAuthorizationUserAllArgs(user);

        Response response = userClient.authorization(userAuthorization);
        response.then().log().all()
                .statusCode(200)
                .and()
                .assertThat().body("success", is(true));
    }

    @Test
    @DisplayName("Error When User Authorization Invalid Email")
    @Description("The test checks the request invalid a email: 401 Unauthorized")
    public void errorWhenUserAuthorizationInvalidEmailTest() {
        userAuthorization = UserAuthorization.getAuthorizationUserInvalidEmail(user);

        Response response = userClient.authorization(userAuthorization);
        response.then().log().all()
                .statusCode(401)
                .and()
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Error When User Authorization Invalid Password")
    @Description("The test checks the request invalid a password: 401 Unauthorized")
    public void errorWhenUserAuthorizationInvalidPasswordTest() {
        userAuthorization = UserAuthorization.getAuthorizationUserInvalidPassword(user);

        Response response = userClient.authorization(userAuthorization);
        response.then().log().all()
                .statusCode(401)
                .and()
                .assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @After
    public void deleteUser() {
        try {
            userClient.deleteUser(token);
        } catch (Exception exception) {
            System.out.println("Ошибка  удаления пользователя.");
        }
    }
}
