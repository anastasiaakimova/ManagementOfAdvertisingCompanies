package by.akimova.ManagementOfAdvertisingCompanies.service;

import by.akimova.ManagementOfAdvertisingCompanies.exception.EntityNotExistException;
import by.akimova.ManagementOfAdvertisingCompanies.exception.EntityNotFoundException;
import by.akimova.ManagementOfAdvertisingCompanies.exception.NotAccessException;
import by.akimova.ManagementOfAdvertisingCompanies.model.Advertiser;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service interface for class {@link Advertiser}.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
public interface AdvertiserService {
    Advertiser saveAdvertiser(Advertiser advertiser);

    Advertiser getById(UUID advId) throws EntityNotFoundException, EntityNotExistException;

    List<Advertiser> getAllAdvertisers(Optional<Integer> page, Optional<Integer> size, Optional<String> sortBy);

    Advertiser updateAdvertiser(UUID advId, Advertiser advertiser) throws EntityNotFoundException, EntityNotExistException, NotAccessException;

    void deleteAdvertiserById(UUID advId) throws NotAccessException;
}