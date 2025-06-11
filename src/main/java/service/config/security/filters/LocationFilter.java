package service.config.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.web.filter.OncePerRequestFilter;
import service.context.LocationContext;
import service.domain.dto.location.CurrentLocation;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public class LocationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String latHeader = request.getHeader("X-Latitude");
        final String lonHeader = request.getHeader("X-Longitude");

        if (Objects.isNull(latHeader) || latHeader.isBlank() || Objects.isNull(lonHeader) || lonHeader.isBlank()) {
            throw new BadRequestException("Latitude and Longitude headers are required");
        }

        try {
            final double lat = Double.parseDouble(latHeader);
            final double lon = Double.parseDouble(lonHeader);
            LocationContext.set(new CurrentLocation(lat, lon));
        } catch (NumberFormatException e) {
            throw new BadRequestException("Latitude and Longitude must be valid decimal numbers");
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            LocationContext.clear();
        }
    }

}
