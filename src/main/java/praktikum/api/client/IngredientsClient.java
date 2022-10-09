package praktikum.api.client;

import io.qameta.allure.Step;
import praktikum.api.util.Ingredients;
import static io.restassured.RestAssured.given;

public class IngredientsClient {
    private final String ROOT = "/ingredients";

    @Step("Send GET request to receiving ingredients: /api/ingredients")
    public Ingredients getIngredients() {
        return given()
                .get(ROOT)
                .body().as(Ingredients.class);
    }
}
