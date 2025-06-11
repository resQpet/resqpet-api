package service.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
@UtilityClass
public class Handlers {

    public <T> Optional<T> silently(Supplier<T> action) {
        try {
            return Optional.of(action.get());
        } catch (Throwable e) {
            log.warn("Exception occurred while silently: {}", e.getMessage());
            return Optional.empty();
        }
    }

    public void silently(Runnable runnable) {
        try {
            runnable.run();
        } catch (Throwable e) {
            log.warn("Exception occurred while silently-runnable: {}", e.getMessage());
        }
    }
}
