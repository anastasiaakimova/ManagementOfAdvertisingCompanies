package by.akimova.ManagementOfAdvertisingCompanies.service.impl;

import by.akimova.ManagementOfAdvertisingCompanies.exception.EntityNotExistException;
import by.akimova.ManagementOfAdvertisingCompanies.model.Advertiser;
import by.akimova.ManagementOfAdvertisingCompanies.repository.AdvertiserRepository;
import by.akimova.ManagementOfAdvertisingCompanies.service.AdvertiserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

/**
 * The class is implementation of  {@link AdvertiserService} interface.
 * Wrapper for {@link AdvertiserRepository} + business logic.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@Service
@Slf4j
@AllArgsConstructor
public class AdvertiserServiceImpl implements AdvertiserService {
    private final AdvertiserRepository advertiserRepository;

    /**
     * The method add new advertiser.
     *
     * @param advertiser This is advertiser with information about it, and it's fields
     * @return Saved advertiser.
     */
    @Override
    public Advertiser saveAdvertiser(Advertiser advertiser) {
        advertiser.setId(UUID.randomUUID());
        log.info("IN saveAdvertiser - new advertiser with id: {} successfully added", advertiser.getId());
        return advertiserRepository.save(advertiser);
    }

    /**
     * The method get advertiser by id with all information about it.
     *
     * @param advId This is advertiser's id.
     * @return found advertiser.
     */
    @Override
    public Advertiser getById(UUID advId) throws EntityNotFoundException, EntityNotExistException {
        if (advId == null) {
            log.error("IN getById - id is null");
            throw new EntityNotExistException("itemId is null");
        }
        Advertiser advertiser = advertiserRepository.findAdvertiserById(advId);
        if (advertiser == null) {
            log.error("IN getById - no advertiser found by id: {}", advId);
            throw new EntityNotFoundException("Advertiser not found");
        }
        log.info("IN getById - campaign: {} found by id: {}", advertiser, advId);
        return advertiser;
    }

    /**
     * The method get all advertisers with all information about it.
     *
     * @return list of advertisers.
     */
    @Override
    public List<Advertiser> getAllAdvertisers() {
        List<Advertiser> result = advertiserRepository.findAll();
        log.info("IN getAllAdvertisers - {} advertisers found", result.size());
        return result;
    }
    /**
     * This method update advertiser.
     *
     * @param advId This is advertiser's id which needed to update.
     * @param advertiser   This is updated advertiser.
     * @return Updated advertiser.
     */
    @Override
    public Advertiser updateAdvertiser(UUID advId, Advertiser advertiser) throws EntityNotFoundException, EntityNotExistException {
        if (advertiser == null) {
            log.error("IN updateAdvertiser - advertiser is null");
            throw new EntityNotExistException("advertiser is null");
        }
        Advertiser dbAdvertiser = advertiserRepository.findAdvertiserById(advId);
        if (dbAdvertiser == null){
            log.error("IN updateAdvertiser - no advertiser found by id: {}", advId);
            throw new EntityNotFoundException( "advertiser is null");
        }
        dbAdvertiser.setName(advertiser.getName());
        dbAdvertiser.setDescription(advertiser.getDescription());
        dbAdvertiser.setUsr(advertiser.getUsr());

        log.info("IN updateAdvertiser - advertiser with id: {} successfully edited ", advId);
        return advertiserRepository.save(dbAdvertiser);
    }

    /**
     * This method delete advertiser.
     *
     * @param advId This is advertiser's id which needed to delete.
     */
    @Override
    public void deleteAdvertiserById(UUID advId) {
        advertiserRepository.deleteAdvertiserById(advId);
        log.info("IN deleteAdvertiserById - advertiser with id: {} successfully deleted", advId);
    }
}