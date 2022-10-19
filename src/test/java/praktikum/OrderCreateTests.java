package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.api.client.IngredientsClient;
import praktikum.api.client.OrderClient;
import praktikum.api.client.UserClient;
import praktikum.api.util.Order;
import praktikum.api.util.User;
import java.util.ArrayList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class OrderCreateTests extends TestBase {
    Order order;
    User user;
    OrderClient orderClient;
    IngredientsClient ingredientsClient;
    UserClient userClient;
    private String token;

    @Before
    public void setUpOrderCreate() {
        orderClient = new OrderClient();
        ingredientsClient = new IngredientsClient();
        userClient = new UserClient();

        user = User.getRandomRegisterUserAllArgs();
        token = userClient.registration(user)
                .then()
                .extract()
                .path("accessToken");
    }

    @Test
    @DisplayName("Success Order Create With Ingredients And With Authorization")
    @Description("The test verifies the successful create of a user with ingredients and with authorization: 200 OK")
    public void successOrderCreateWithIngredientsAndWithAuthorizationTest() {
        ArrayList<String> ingredients = getIngredients();

        order = Order.getOrderWithValidHash(ingredients);

        Response response = orderClient.createOrderWithAuthorization(order, token);
        response.then().log().all()
                .statusCode(200)
                .and()
                .assertThat().body("success", is(true));
    }

    @Test
    @DisplayName("Error Order Create With Ingredients And Without Authorization")
    @Description("The test verifies the successful create of a user with ingredients and without authorization: 401 Unauthorized")
    public void errorOrderCreateWithIngredientsAndWithoutAuthorizationTest() {
        ArrayList<String> ingredients = getIngredients();

        order = Order.getOrderWithValidHash(ingredients);

        Response response = orderClient.createOrderWithoutAuthorization(order);
        response.then().log().all()
                .statusCode(401)
                .and()
                .assertThat().body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Error Order Create Without Ingredients And With Authorization")
    @Description("The test verifies the successful create of a user without ingredients and with authorization: 400 Bad Request")
    public void errorOrderCreateWithoutIngredientsAndWithAuthorizationTest() {
        order = Order.getOrderWithHashIsNull();

        Response response = orderClient.createOrderWithAuthorization(order, token);
        response.then().log().all()
                .statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Success Order Create With Invalid Hash Ingredients And With Authorization")
    @Description("The test verifies the successful create of a user with invalid hash ingredients and with authorization: 500 Internal Server Error")
    public void successOrderCreateWithInvalidHashIngredientsAndWithAuthorizationTest() {
        order = Order.getOrderWithInvalidHash();

        Response response = orderClient.createOrderWithAuthorization(order, token);
        response.then().log().all()
                .statusCode(500);
    }

    private ArrayList<String> getIngredients() {
        String idIngredient = ingredientsClient.getIngredients().getData().get(0).get_id();
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add(idIngredient);

        return ingredients;
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
