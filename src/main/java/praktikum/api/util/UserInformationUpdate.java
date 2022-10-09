package praktikum.api.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInformationUpdate {
    private String email;
    private String password;
    private String name;

    public static UserInformationUpdate getUpdateUserAllArgs() {
        return new UserInformationUpdate(
                RandomStringUtils.randomAlphabetic(2) + "@" + RandomStringUtils.randomAlphabetic(2) + "." + RandomStringUtils.randomAlphabetic(2),
                "newpassword",
                RandomStringUtils.randomAlphabetic(4)
        );
    }

    public static UserInformationUpdate getUpdateEmailUser(User user) {
        return new UserInformationUpdate(
                RandomStringUtils.randomAlphabetic(2) + "@" + RandomStringUtils.randomAlphabetic(2) + "." + RandomStringUtils.randomAlphabetic(2),
                user.getPassword(),
                user.getName()
        );
    }

    public static UserInformationUpdate getUpdatePasswordUser(User user) {
        return new UserInformationUpdate(
                user.getEmail(),
                "newpassword",
                user.getName()
        );
    }

    public static UserInformationUpdate getUpdateNameUser(User user) {
        return new UserInformationUpdate(
                user.getEmail(),
                user.getPassword(),
                RandomStringUtils.randomAlphabetic(4)
        );
    }
}
