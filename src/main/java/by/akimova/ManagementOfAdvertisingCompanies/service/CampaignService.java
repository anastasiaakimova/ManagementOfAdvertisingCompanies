package by.akimova.ManagementOfAdvertisingCompanies.service;

import by.akimova.ManagementOfAdvertisingCompanies.exception.EntityNotExistException;
import by.akimova.ManagementOfAdvertisingCompanies.exception.EntityNotFoundException;
import by.akimova.ManagementOfAdvertisingCompanies.model.Campaign;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service interface for class {@link Campaign}.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
public interface CampaignService {
    Campaign saveCampaign(Campaign campaign);

    Campaign getById(UUID campId) throws  EntityNotExistException, EntityNotFoundException;

    List<Campaign> getAllCampaigns(Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy);

    Campaign updateCampaign(UUID campId, Campaign campaign) throws EntityNotFoundException, EntityNotExistException;

    void deleteCampaignById(UUID campId);
}