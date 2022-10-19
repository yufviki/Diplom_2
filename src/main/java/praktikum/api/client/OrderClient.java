package praktikum.api.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import praktikum.api.util.Order;
import static io.restassured.RestAssured.given;

public class OrderClient {
    private final String ROOT = "/orders";

    @Step("Send POST request to create an order with authorization: /api/orders")
    public Response createOrderWithAuthorization(Order order, String token) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .and()
                .body(order)
                .when()
                .post(ROOT);
    }

    @Step("Send POST request to create an order without authorization: /api/orders")
    public Response createOrderWithoutAuthorization(Order order) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post(ROOT);
    }

    @Step("Send GET request to receiving user orders with authorization: /api/orders")
    public Response getOrderUserWithAuthorization(String token) {
        return given()
                .header("Authorization", token)
                .and()
                .get(ROOT);
    }

    @Step("Send GET request to receiving user orders without authorization: /api/orders")
    public Response getOrderUserWithoutAuthorization() {
        return given()
                .get(ROOT);
    }
}
