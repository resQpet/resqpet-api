package service.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GeoDistanceUtil {

    private static final int EARTH_RADIUS_KM = 6371;

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }

    public double calculateDistanceFromHeader(HttpServletRequest request, double targetLat, double targetLon) {
        String locationHeader = request.getHeader("X-Auth-Location");

        if (locationHeader == null || !locationHeader.contains(",")) {
            return Double.MAX_VALUE;
        }

        try {
            String[] parts = locationHeader.split(",");
            double originLat = Double.parseDouble(parts[0]);
            double originLon = Double.parseDouble(parts[1]);

            return calculateDistance(originLat, originLon, targetLat, targetLon);
        } catch (Exception e) {
            return Double.MAX_VALUE;
        }
    }
}
