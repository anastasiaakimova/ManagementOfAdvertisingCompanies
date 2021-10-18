package by.akimova.ManagementOfAdvertisingCompanies.controller;

import by.akimova.ManagementOfAdvertisingCompanies.exception.EntityNotExistException;
import by.akimova.ManagementOfAdvertisingCompanies.exception.EntityNotFoundException;
import by.akimova.ManagementOfAdvertisingCompanies.model.Advertiser;
import by.akimova.ManagementOfAdvertisingCompanies.service.AdvertiserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller advertiser connected requests.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@RestController
@RequestMapping("/advertisers")
@AllArgsConstructor
@Slf4j
public class AdvertiserController {
    private final AdvertiserService advertiserService;

    /**
     * The method get all advertisers.
     *
     * @return ResponseEntity with list of advertisers and status ok.
     */
    @GetMapping
    ResponseEntity<List<Advertiser>> getAllAdvertisers( @RequestParam Optional<Integer> page,
                                                        @RequestParam Optional<Integer> size,
                                                        @RequestParam Optional<String> sortBy) {
        return ResponseEntity.ok(advertiserService.getAllAdvertisers(page, size, sortBy));
    }

    /**
     * The method get advertiser by id.
     *
     * @param id This is advertiser's id which should be viewed.
     * @return ResponseEntity with body of advertiser and status ok.
     */

    @GetMapping("/{id}")
    ResponseEntity<?> getAdvertiserById(@PathVariable(value = "id") UUID id) {
        Advertiser advertiser;
        try {
            advertiser = advertiserService.getById(id);
        } catch (EntityNotExistException e) {
            log.error("IN AdvertiserController getAdvertiserById - id is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            log.error("IN AdvertiserController getAdvertiserById - advertiser by id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(advertiser, HttpStatus.OK);
    }

    /**
     * The method add new advertiser.
     *
     * @param advertiser This is advertiser with its information and body.
     * @return response with body of created advertiser and status ok.
     */
    @PostMapping
    public ResponseEntity<?> addAdvertiser(@RequestBody Advertiser advertiser) {
        Advertiser savedAdvertiser;
        savedAdvertiser = advertiserService.saveAdvertiser(advertiser);
        return new ResponseEntity<>(savedAdvertiser, HttpStatus.CREATED);
    }

    /**
     * The method update advertiser.
     *
     * @param id         This is advertiser's id which should be updated.
     * @param advertiser This is new body for advertiser which should be updated.
     * @return response with body of updated advertiser and status ok.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAdvertiser(@PathVariable(value = "id") UUID id, @RequestBody Advertiser advertiser) {
        Advertiser updatedAdvertiser;
        try {
            updatedAdvertiser = advertiserService.updateAdvertiser(id, advertiser);
        } catch (EntityNotExistException e) {
            log.error("IN AdvertiserController updateAdvertiser - id is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            log.error("IN AdvertiserController updateAdvertiser - advertiser by id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedAdvertiser, HttpStatus.OK);
    }

    /**
     * The method delete advertiser.
     *
     * @param id This is advertiser's id which should be deleted.
     * @return response status no_content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdvertiser(@PathVariable(value = "id") UUID id) {
        advertiserService.deleteAdvertiserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}