package service.context;

import lombok.experimental.UtilityClass;
import service.domain.dto.location.CurrentLocation;

@UtilityClass
public class LocationContext {

    private static final ThreadLocal<CurrentLocation> CONTEXT = new ThreadLocal<>();

    public void set(CurrentLocation currentLocation) {
        CONTEXT.set(currentLocation);
    }

    public CurrentLocation get() {
        return CONTEXT.get();
    }

    public void clear() {
        CONTEXT.remove();
    }
}
