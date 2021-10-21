package by.akimova.ManagementOfAdvertisingCompanies.service.impl;

import by.akimova.ManagementOfAdvertisingCompanies.exception.EntityNotExistException;
import by.akimova.ManagementOfAdvertisingCompanies.exception.NotAccessException;
import by.akimova.ManagementOfAdvertisingCompanies.model.Advertiser;
import by.akimova.ManagementOfAdvertisingCompanies.model.User;
import by.akimova.ManagementOfAdvertisingCompanies.repository.AdvertiserRepository;
import by.akimova.ManagementOfAdvertisingCompanies.repository.UserRepository;
import by.akimova.ManagementOfAdvertisingCompanies.service.AdvertiserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private final UserRepository userRepository;

    /**
     * The method add new advertiser.
     *
     * @param advertiser This is advertiser with information about it, and it's fields
     * @return Saved advertiser.
     */
    @Override
    public Advertiser saveAdvertiser(Advertiser advertiser) {
        advertiser.setId(UUID.randomUUID());
        advertiser.setIsActive(Boolean.TRUE);
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
     * @return list of active advertisers.
     */
    @Override
    public List<Advertiser> getAllAdvertisers(Optional<Integer> page,
                                              Optional<Integer> size,
                                              Optional<String> sortBy) {

        List<Advertiser> result = advertiserRepository.findAll(PageRequest.of(page.orElse(0),
                        size.orElse(advertiserRepository.findAll().size()),
                        Sort.Direction.ASC, sortBy.orElse("id")))
                .stream()
                .filter(Advertiser::getIsActive)
                .collect(Collectors.toList());

        log.info("IN getAllAdvertisers - {} advertisers found", result.size());
        return result;
    }

    /**
     * This method update advertiser.
     *
     * @param advId      This is advertiser's id which needed to update.
     * @param advertiser This is updated advertiser.
     * @return Updated advertiser.
     */
    @Override
    public Advertiser updateAdvertiser(UUID advId, Advertiser advertiser) throws EntityNotFoundException, EntityNotExistException, NotAccessException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        if (advertiser == null) {
            log.error("IN updateAdvertiser - advertiser is null");
            throw new EntityNotExistException("advertiser is null");
        }

        Advertiser dbAdvertiser = advertiserRepository.findAdvertiserById(advId);

        if (dbAdvertiser == null) {
            log.error("IN updateAdvertiser - no advertiser found by id: {}", advId);
            throw new EntityNotFoundException("advertiser is null");
        }

        User user = userRepository.findUserById(dbAdvertiser.getUsr());

        if (Objects.equals(username, user.getMail())) {

            dbAdvertiser.setName(advertiser.getName());
            dbAdvertiser.setDescription(advertiser.getDescription());
            dbAdvertiser.setUsr(advertiser.getUsr());
            log.info("IN updateAdvertiser - advertiser with id: {} successfully edited ", advId);
        } else {
            log.error("IN updateCart - This username can't update this cart");
            throw new NotAccessException("Username is invalid");
        }

        return advertiserRepository.save(dbAdvertiser);
    }

    /**
     * This method delete advertiser.
     * The parameter "isActive" turn false and this means that advertiser deleted.
     *
     * @param advId This is advertiser's id which needed to delete.
     */
    @Override
    public void deleteAdvertiserById(UUID advId) throws NotAccessException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Advertiser advertiser = advertiserRepository.findAdvertiserById(advId);

        User user = userRepository.findUserById(advertiser.getUsr());

        if (Objects.equals(username, user.getMail())) {

            advertiser.setIsActive(Boolean.FALSE);
            advertiserRepository.save(advertiser);
            log.info("IN deleteAdvertiserById - advertiser with id: {} successfully deleted", advId);
        } else {
            log.error("IN updateCart - This username can't update this cart");
            throw new NotAccessException("Username is invalid");
        }
    }
}