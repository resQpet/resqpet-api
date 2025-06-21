package service.config.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import service.context.LocationContext;
import service.domain.constants.AuthConstants;
import service.domain.dto.location.CurrentLocation;
import service.domain.exceptions.ForbiddenException;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Component
@AllArgsConstructor
public class LocationFilter extends BaseFilter {

    private final ObjectMapper mapper;

    @Override
    public void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) {
        final String latHeader = request.getHeader("X-Latitude");
        final String lonHeader = request.getHeader("X-Longitude");

        if (Objects.isNull(latHeader) || latHeader.isBlank() || Objects.isNull(lonHeader) || lonHeader.isBlank()) {
            this.handleError(mapper, response, new ForbiddenException("Latitude and Longitude headers are required"));
            return;
        }

        try {
            final double lat = Double.parseDouble(latHeader);
            final double lon = Double.parseDouble(lonHeader);
            LocationContext.set(new CurrentLocation(lat, lon));
            filterChain.doFilter(request, response);
        } catch (NumberFormatException e) {
            this.handleError(mapper, response, new ForbiddenException("Latitude and Longitude must be valid decimal numbers"));
        } catch (Throwable e) {
            log.error("Error processing location headers: {}", e.getMessage(), e);
            this.handleError(mapper, response, e);
        } finally {
            LocationContext.clear();
        }
    }

    @Override
    public boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return Arrays.stream(AuthConstants.PUBLIC_URLS).anyMatch(url -> request.getRequestURI().substring(request.getContextPath().length()).equals(url));
    }
}
