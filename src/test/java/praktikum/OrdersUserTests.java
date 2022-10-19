package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.api.client.OrderClient;
import praktikum.api.client.UserClient;
import praktikum.api.util.User;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class OrdersUserTests extends TestBase {
    User user;
    UserClient userClient;
    OrderClient orderClient;
    private String token;

    @Before
    public void setUpOrder() {
        userClient = new UserClient();
        orderClient = new OrderClient();

        user = User.getRandomRegisterUserAllArgs();
        token = userClient.registration(user)
                .then()
                .extract()
                .path("accessToken");
    }

    @Test
    @DisplayName("Success Get Orders User With Authorization")
    @Description("The test verifies the successful get orders user with authorization: 200 OK")
    public void successGetOrdersUserWithAuthorizationTest() {
        Response response = orderClient.getOrderUserWithAuthorization(token);
        response.then().log().all()
                .statusCode(200)
                .and()
                .assertThat().body("success", is(true));
    }

    @Test
    @DisplayName("Error Get Orders User Without Authorization")
    @Description("The test verifies the successful get orders user without authorization: 401 Unauthorized")
    public void errorGetOrdersUserWithoutAuthorizationTest() {
        Response response = orderClient.getOrderUserWithoutAuthorization();
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
