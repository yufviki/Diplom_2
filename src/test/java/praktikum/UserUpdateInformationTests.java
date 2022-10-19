package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.api.client.UserClient;
import praktikum.api.util.User;
import praktikum.api.util.UserInformationUpdate;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class UserUpdateInformationTests extends TestBase {
    User user;
    UserInformationUpdate userInformationUpdate;
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
    @DisplayName("Success Update All Information User With Authorization")
    @Description("The test verifies the successful update all information of a user with authorization: 200 OK")
    public void successUpdateAllInformationUserWithAuthorizationTest() {
        userInformationUpdate = UserInformationUpdate.getUpdateUserAllArgs();

        Response response = userClient.updateUserInformationWithAuthorization(userInformationUpdate, token);
        response.then().log().all()
                .statusCode(200)
                .and()
                .assertThat().body("success", is(true));
    }

    @Test
    @DisplayName("Success Update Email Information User With Authorization")
    @Description("The test verifies the successful update email information of a user with authorization: 200 OK")
    public void successUpdateEmailInformationUserWithAuthorizationTest() {
        userInformationUpdate = UserInformationUpdate.getUpdateEmailUser(user);

        Response response = userClient.updateUserInformationWithAuthorization(userInformationUpdate, token);
        response.then().log().all()
                .statusCode(200)
                .and()
                .assertThat().body("success", is(true));
    }

    @Test
    @DisplayName("Success Update Password Information User With Authorization")
    @Description("The test verifies the successful update password information of a user with authorization: 200 OK")
    public void successUpdatePasswordInformationUserWithAuthorizationTest() {
        userInformationUpdate = UserInformationUpdate.getUpdatePasswordUser(user);

        Response response = userClient.updateUserInformationWithAuthorization(userInformationUpdate, token);
        response.then().log().all()
                .statusCode(200)
                .and()
                .assertThat().body("success", is(true));
    }

    @Test
    @DisplayName("Success Update Name Information User With Authorization")
    @Description("The test verifies the successful update name information of a user with authorization: 200 OK")
    public void successUpdateNameInformationUserWithAuthorizationTest() {
        userInformationUpdate = UserInformationUpdate.getUpdateNameUser(user);

        Response response = userClient.updateUserInformationWithAuthorization(userInformationUpdate, token);
        response.then().log().all()
                .statusCode(200)
                .and()
                .assertThat().body("success", is(true));
    }

    @Test
    @DisplayName("Error Update Information User Without Authorization")
    @Description("The test verifies the successful update information of a user without authorization: 401 Unauthorized")
    public void errorUpdateInformationUserWithoutAuthorizationTest() {
        userInformationUpdate = UserInformationUpdate.getUpdateUserAllArgs();

        Response response = userClient.updateUserInformationWithoutAuthorization(userInformationUpdate);
        response.then().log().all()
                .statusCode(401)
                .and()
                .assertThat().body("message", equalTo("You should be authorised"));
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
