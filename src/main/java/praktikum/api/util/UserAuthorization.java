package praktikum.api.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthorization {
    private String email;
    private String password;

    public static UserAuthorization getAuthorizationUserAllArgs(User user) {
        return new UserAuthorization(
                user.getEmail(),
                user.getPassword()
        );
    }

    public static UserAuthorization getAuthorizationUserInvalidEmail(User user) {
        return new UserAuthorization(
                "Invalid",
                user.getPassword()
        );
    }

    public static UserAuthorization getAuthorizationUserInvalidPassword(User user) {
        return new UserAuthorization(
                user.getEmail(),
                "Invalid"
        );
    }
}
