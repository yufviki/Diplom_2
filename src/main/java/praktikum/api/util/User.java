package praktikum.api.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String email;
    private String password;
    private String name;

    public static User getRandomRegisterUserAllArgs() {
        return new User(
                RandomStringUtils.randomAlphabetic(4) + "@" + RandomStringUtils.randomAlphabetic(4) + "." + RandomStringUtils.randomAlphabetic(2),
                "password",
                RandomStringUtils.randomAlphabetic(6)
        );
    }

    public static User getRandomRegisterUserWithoutEmail() {
        return new User(
                null,
                "password",
                RandomStringUtils.randomAlphabetic(6)
        );
    }

    public static User getRandomRegisterUserWithoutPassword() {
        return new User(
                RandomStringUtils.randomAlphabetic(4) + "@" + RandomStringUtils.randomAlphabetic(4) + "." + RandomStringUtils.randomAlphabetic(2),
                null,
                RandomStringUtils.randomAlphabetic(6)
        );
    }

    public static User getRandomRegisterUserWithoutName() {
        return new User(
                RandomStringUtils.randomAlphabetic(4) + "@" + RandomStringUtils.randomAlphabetic(4) + "." + RandomStringUtils.randomAlphabetic(2),
                "password",
                null
        );
    }
}
