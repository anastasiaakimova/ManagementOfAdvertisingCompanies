package by.akimova.ManagementOfAdvertisingCompanies.repository;

import by.akimova.ManagementOfAdvertisingCompanies.model.Advertiser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for class {@link Advertiser}.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@Repository
public interface AdvertiserRepository extends JpaRepository<Advertiser, String> {
    void deleteAdvertiserById(UUID advId);

    Advertiser findAdvertiserById(UUID advId);
}