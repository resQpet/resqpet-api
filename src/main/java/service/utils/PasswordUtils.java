package service.utils;

import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class PasswordUtils {

    public static boolean isValidPassword(String password) {
        return Objects.nonNull(password) &&
                password.length() >= 8 &&
                password.length() <= 64 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*");
    }
}
