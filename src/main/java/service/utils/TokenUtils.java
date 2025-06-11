package service.utils;

import lombok.experimental.UtilityClass;
import service.domain.exceptions.BadRequestException;

import java.util.Objects;

@UtilityClass
public class TokenUtils {

    public String TOKEN_TYPE = "Bearer";

    public String getToken(String header) {
        if (Objects.isNull(header)) {
            return null;
        } else if (header.startsWith(TOKEN_TYPE)) {
            final int index = TOKEN_TYPE.length() + 1;
            return header.substring(index);
        } else {
            throw new BadRequestException("Invalid token type");
        }
    }
}
