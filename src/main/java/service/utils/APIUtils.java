package service.utils;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class APIUtils {

    /**
     * This method generates a unique identifier using a randomly generated UUID.
     *
     * @return A String representing the unique identifier.
     */
    public String genID() {
        return UUID.randomUUID().toString();
    }
}
