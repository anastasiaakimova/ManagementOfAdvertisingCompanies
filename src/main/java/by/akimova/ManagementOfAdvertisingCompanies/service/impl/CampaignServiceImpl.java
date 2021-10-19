package by.akimova.ManagementOfAdvertisingCompanies.service.impl;

import by.akimova.ManagementOfAdvertisingCompanies.exception.EntityNotExistException;
import by.akimova.ManagementOfAdvertisingCompanies.exception.EntityNotFoundException;
import by.akimova.ManagementOfAdvertisingCompanies.model.Campaign;
import by.akimova.ManagementOfAdvertisingCompanies.repository.CampaignRepository;
import by.akimova.ManagementOfAdvertisingCompanies.service.CampaignService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The class is implementation of user's business logic.
 * The class is implementation of  {@link CampaignService} interface.
 * Wrapper for {@link CampaignRepository} + business logic.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */

@Service
@Slf4j
@AllArgsConstructor
public class CampaignServiceImpl implements CampaignService {
    CampaignRepository campaignRepository;

    /**
     * The method add new campaign.
     *
     * @param campaign This is campaign with information about it, and it's fields
     * @return Saved campaign.
     */
    @Override
    public Campaign saveCampaign(Campaign campaign) {
        campaign.setId(UUID.randomUUID());
        campaign.setIsActive(Boolean.TRUE);
        log.info("IN saveCampaign - new campaign with id: {} successfully added", campaign.getId());
        return campaignRepository.save(campaign);
    }

    /**
     * The method get campaign by id with all information about it.
     *
     * @param campId This is campaign's id.
     * @return found advertiser.
     */
    @Override
    public Campaign getById(UUID campId) throws EntityNotFoundException, EntityNotExistException {
        if (campId == null) {
            log.error("IN getById - id is null");
            throw new EntityNotExistException("campId is null");
        }
        Campaign campaign = campaignRepository.findCampaignById(campId);
        if (campaign == null) {
            log.error("IN getById - no campaign found by id: {}", campId);
            throw new EntityNotFoundException("Campaign not found");
        }
        log.info("IN getById - campaign: {} found by id: {}", campaign, campId);
        return campaign;
    }


    /**
     * The method get all campaign with all information about it.
     *
     * @return list of active campaigns.
     */
    @Override
    public List<Campaign> getAllCampaigns(Optional<Integer> page,
                                          Optional<Integer> size,
                                          Optional<String> sortBy) {
        List<Campaign> result = campaignRepository.findAll(PageRequest.of(page.orElse(0),
                        size.orElse(campaignRepository.findAll().size()),
                        Sort.Direction.ASC, sortBy.orElse("id")))
                .stream()
                .filter(Campaign::getIsActive)
                .collect(Collectors.toList());
        log.info("IN getAllCampaigns - {} campaigns found", result.size());
        return result;
    }

    /**
     * This method update campaign.
     *
     * @param campId   This is campaign's id which needed to update.
     * @param campaign This is updated campaign.
     * @return Updated campaign.
     */
    @Override
    public Campaign updateCampaign(UUID campId, Campaign campaign) throws EntityNotFoundException, EntityNotExistException {
        if (campaign == null) {
            log.error("IN updateCampaign - campaign is null");
            throw new EntityNotExistException("campaign is null");
        }
        Campaign dbCampaign = campaignRepository.findCampaignById(campId);
        if (dbCampaign == null) {
            log.error("IN updateCampaign - no campaign found by id: {}", campId);
            throw new EntityNotFoundException("campaign is null");
        }
        dbCampaign.setName(campaign.getName());
        dbCampaign.setLink(campaign.getLink());
        dbCampaign.setAge(campaign.getAge());
        dbCampaign.setCountry(campaign.getCountry());
        dbCampaign.setLanguage(campaign.getLanguage());
        dbCampaign.setGeolocation(campaign.getGeolocation());
        dbCampaign.setAdvertiser(campaign.getAdvertiser());

        log.info("IN updateCampaign - campaign with id: {} successfully edited", campId);
        return campaignRepository.save(dbCampaign);
    }

    /**
     * This method delete campaign.
     * The parameter "isActive" turn false and this means that campaign deleted.
     *
     * @param campId This is campaign's id which needed to delete.
     */
    @Override
    public void deleteCampaignById(UUID campId) {
        Campaign campaign = campaignRepository.findCampaignById(campId);
        campaign.setIsActive(Boolean.FALSE);
        campaignRepository.save(campaign);

        log.info("IN deleteUserById - campaign with id: {} successfully deleted", campId);
    }
}