package service.utils;

import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class Numbers {

    public int ONE = 1;
    public int ZERO = 0;

    public boolean isPositive(Number number) {
        return Objects.nonNull(number) && number.doubleValue() > 0;
    }

    public static double getPercentage(Integer part, Integer total) {
        if (isPositive(part) && isPositive(total)) {
            return part.doubleValue() / total.doubleValue() * 100;
        }
        return ZERO;
    }

}
