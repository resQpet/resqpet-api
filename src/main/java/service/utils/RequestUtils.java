package service.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;


@Slf4j
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestUtils {

    private final HttpServletRequest request;
    public static final String USER_AGENT = "User-Agent";
    public static final String DEFAULT_VALUE = "UNAVAILABLE";
    public static final String X_FORWARDED_FOR = "X-Forwarded-For";

    public static RequestUtils of(HttpServletRequest request) {
        return new RequestUtils(request);
    }

    /**
     * Gets the remote address of the client making the HTTP request.
     * <p>
     * This method first tries to retrieve the client's IP address from the X-Forwarded-For header in the request.
     * If the header is not present or empty, the method falls back to obtaining the address from the request object itself.
     * The method then splits the value by comma and returns the first IP address.
     * If an exception occurs during the process, the method logs an error and returns a default address.
     *
     * @return The remote address of the client making the HTTP request or a default address if unable to retrieve.
     */
    public String getRemoteAddress() {
        try {
            return Optional.ofNullable(request.getHeader(X_FORWARDED_FOR))
                    .orElseGet(request::getRemoteAddr)
                    .split(Chars.COMMA)[0];
        } catch (Throwable e) {
            log.error("Unable to get remote address", e);
            return DEFAULT_VALUE;
        }
    }

    /**
     * Retrieves the User-Agent header value from the HTTP request or returns a default value if not present.
     *
     * @return The User-Agent header value from the HTTP request or a default value if not present.
     */
    public String getUserAgent() {
        return Optional.ofNullable(request.getHeader(USER_AGENT)).orElse(DEFAULT_VALUE);
    }
}
