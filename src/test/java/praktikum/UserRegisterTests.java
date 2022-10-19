package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.api.client.UserClient;
import praktikum.api.util.User;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class UserRegisterTests extends TestBase {
    User user;
    UserClient userClient;
    private String token;

    @Before
    public void setUpUserClient() {
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Success User Register")
    @Description("The test verifies the successful registration of a user: 200 OK")
    public void successUserRegisterTest() {
        user = User.getRandomRegisterUserAllArgs();

        Response response = userClient.registration(user);

        token = response
                .then()
                .extract()
                .path("accessToken");

        response.then().log().all()
                .statusCode(200)
                .and()
                .assertThat().body("success", is(true));
    }

    @Test
    @DisplayName("Error When User Register Identical")
    @Description("The test checks a request with a duplicate email: 403 Forbidden")
    public void errorWhenUserRegisterIdenticalTest() {
        user = User.getRandomRegisterUserAllArgs();

        token = userClient.registration(user)
                .then()
                .extract()
                .path("accessToken");

        Response response = userClient.registration(user);
        response.then().log().all()
                .statusCode(403)
                .and()
                .assertThat().body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Error When User Register Without Email")
    @Description("The test checks the request without a email: 403 Forbidden")
    public void errorWhenUserRegisterWithoutEmailTest() {
        user = User.getRandomRegisterUserWithoutEmail();

        Response response = userClient.registration(user);
        response.then().log().all()
                .statusCode(403)
                .and()
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Error When User Register Without Password")
    @Description("The test checks the request without a password: 403 Forbidden")
    public void errorWhenUserRegisterWithoutPasswordTest() {
        user = User.getRandomRegisterUserWithoutPassword();

        Response response = userClient.registration(user);
        response.then().log().all()
                .statusCode(403)
                .and()
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Error When User Register Without Name")
    @Description("The test checks the request without a name: 403 Forbidden")
    public void errorWhenUserRegisterWithoutNameTest() {
        user = User.getRandomRegisterUserWithoutName();

        Response response = userClient.registration(user);
        response.then().log().all()
                .statusCode(403)
                .and()
                .assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @After
    public void deleteUser() {
        try {
            userClient.deleteUser(token);
        } catch (Exception exception) {
            System.out.println("Ошибка  удаления пользователя. Пользователь не создавался.");
        }
    }
}