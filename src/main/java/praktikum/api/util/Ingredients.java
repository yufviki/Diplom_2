package praktikum.api.util;

import lombok.Data;
import java.util.List;

@Data
public class Ingredients {
    private String success;
    private List <DataIngredient> data;
}
