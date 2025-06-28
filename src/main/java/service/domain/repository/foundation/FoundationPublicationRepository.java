package service.domain.repository.foundation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import service.domain.entity.foundation.FoundationPublication;
import service.domain.repository.BaseRepository;

public interface FoundationPublicationRepository extends BaseRepository<FoundationPublication> {

    @Query(value = """
                    SELECT 
                        fp.*,
                        (6371 * acos(
                            cos(radians(:latUser)) * 
                            cos(radians(fl.LATITUDE)) * 
                            cos(radians(fl.LONGITUDE) - radians(:lonUser)) +
                            sin(radians(:latUser)) * sin(radians(fl.LATITUDE))
                        )) AS distance_km
                    FROM RESQPET.foundation_publication fp
                    JOIN RESQPET.foundation f ON fp.FOUNDATION_ID = f.ID
                    JOIN RESQPET.foundation_location fl ON f.ID = fl.FOUNDATION_ID
                    ORDER BY distance_km ASC, fp.CREATED_AT DESC
                    /*#pageable*/
                    """,
            countQuery = """
                    SELECT count(*)
                    FROM RESQPET.foundation_publication fp
                    JOIN RESQPET.foundation f ON fp.FOUNDATION_ID = f.ID
                    JOIN RESQPET.foundation_location fl ON f.ID = fl.FOUNDATION_ID
                    """,
            nativeQuery = true
    )
    Page<FoundationPublication> findNearbyPublications(
            @Param("latUser") double latUser,
            @Param("lonUser") double lonUser,
            Pageable pageable
    );


}
