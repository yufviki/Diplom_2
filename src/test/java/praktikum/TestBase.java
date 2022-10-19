package praktikum;

import io.restassured.RestAssured;
import org.junit.Before;

public class TestBase {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        RestAssured.basePath ="/api";
    }
}
