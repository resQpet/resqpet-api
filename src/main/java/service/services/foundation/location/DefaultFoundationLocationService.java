package service.services.foundation.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.domain.entity.foundation.Foundation;
import service.domain.entity.foundation.FoundationLocation;
import service.domain.repository.foundation.FoundationLocationRepository;
import service.domain.request.foundation.FoundationLocationRequest;
import service.services.DefaultBaseService;

import java.util.List;

@Getter
@Service
@AllArgsConstructor
public class DefaultFoundationLocationService extends DefaultBaseService<FoundationLocation> implements FoundationLocationService {

    private final FoundationLocationRepository repository;

    /**
     * Creates and saves multiple FoundationLocation entities associated with the given Foundation
     * based on the provided list of FoundationLocationRequest objects. Each FoundationLocationRequest
     * is converted into a FoundationLocation entity, which is then persisted.
     *
     * @param foundation The Foundation entity to associate with the FoundationLocation entities.
     *                   This parameter cannot be null and serves as the parent entity for the created locations.
     * @param location   A list of FoundationLocationRequest objects containing details of each location to be created.
     *                   Each request must include valid values for all required fields such as address, city, country,
     *                   postalCode, latitude, and longitude.
     */
    @Override
    @Transactional
    public void create(Foundation foundation, List<FoundationLocationRequest> location) {
        for (final FoundationLocationRequest request : location) {
            final FoundationLocation locationEntity = FoundationLocation.builder()
                    .foundation(foundation)
                    .address(request.getAddress())
                    .city(request.getCity())
                    .country(request.getCountry())
                    .postalCode(request.getPostalCode())
                    .latitude(request.getLatitude())
                    .longitude(request.getLongitude())
                    .build();
            this.create(locationEntity);
        }

    }
}
