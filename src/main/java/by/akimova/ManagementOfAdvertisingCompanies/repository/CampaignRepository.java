package by.akimova.ManagementOfAdvertisingCompanies.repository;

import by.akimova.ManagementOfAdvertisingCompanies.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for class {@link Campaign}.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@Repository
public interface CampaignRepository extends JpaRepository<Campaign, String> {
    void deleteCampaignById(UUID campId);

    Campaign findCampaignById(UUID campId);
}