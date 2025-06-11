package service.utils;

import lombok.experimental.UtilityClass;
import service.domain.exceptions.BadRequestException;

import java.util.Objects;

@UtilityClass
public class ValidationUtil {

    public static void validatePhoneNumber(String phone) {
        if (Objects.nonNull(phone) && !phone.matches("\\d+")) {
            throw new BadRequestException("El número de teléfono debe contener solo números");
        }
    }

}
