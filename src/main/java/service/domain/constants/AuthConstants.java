package service.domain.constants;


public class AuthConstants {
    public static final String[] PUBLIC_URLS = {"/", "/users/register"};
    public static final String RNC_HEADER = "X-Auth-Company";
    public static final String[] ALLOWED_URLS = {
            "/",
            "/mobiles",
            "/auth/token",
            "/auth/face",
            "/users/register",
            "/account/recover",
            "/account/recover/send",
            "/mobile-devices/exists",
            "/mobile-devices/register",
            "/mobile-devices/validate",
            "/account/recover/validate",
    };
}
