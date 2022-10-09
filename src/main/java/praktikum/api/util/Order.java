package praktikum.api.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private ArrayList<String> ingredients;

    public static Order getOrderWithValidHash(ArrayList<String> ingredients) {
        return new Order(
                ingredients
        );
    }

    public static Order getOrderWithInvalidHash() {
        ArrayList<String> ingredients = new ArrayList<>();
        ingredients.add("ingredients");

        return new Order(
                ingredients
        );
    }

    public static Order getOrderWithHashIsNull() {
        return new Order(
                null
        );
    }
}
