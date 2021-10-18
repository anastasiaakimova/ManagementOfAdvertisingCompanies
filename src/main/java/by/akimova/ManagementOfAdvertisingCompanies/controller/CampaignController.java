package by.akimova.ManagementOfAdvertisingCompanies.controller;

import by.akimova.ManagementOfAdvertisingCompanies.exception.EntityNotExistException;
import by.akimova.ManagementOfAdvertisingCompanies.exception.EntityNotFoundException;
import by.akimova.ManagementOfAdvertisingCompanies.model.Campaign;
import by.akimova.ManagementOfAdvertisingCompanies.service.CampaignService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller campaign connected requests.
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@RestController
@RequestMapping("/campaigns")
@AllArgsConstructor
@Slf4j
public class CampaignController {
    private final CampaignService campaignService;

    /**
     * The method get all campaigns.
     *
     * @return ResponseEntity with list of campaigns and status ok.
     */
    @GetMapping
    ResponseEntity<List<Campaign>> getAllCampaigns(@RequestParam Optional<Integer> page,
                                                   @RequestParam Optional<Integer> size,
                                                   @RequestParam Optional<String> sortBy) {
        return ResponseEntity.ok(campaignService.getAllCampaigns(page, size, sortBy));
    }

    /**
     * The method get campaign by id.
     *
     * @param id This is campaign's id which should be viewed.
     * @return ResponseEntity with body of campaign and status ok.
     */

    @GetMapping("/{id}")
    ResponseEntity<?> getCampaignById(@PathVariable(value = "id") UUID id) {
        Campaign campaign;
        try {
            campaign = campaignService.getById(id);
        } catch (EntityNotExistException e) {
            log.error("IN CampaignController getCampaignById - id is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            log.error("IN CampaignController getCampaignById - campaign by id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(campaign, HttpStatus.OK);
    }

    /**
     * The method add new campaign.
     *
     * @param campaign This is campaign with its information and body.
     * @return response with body of created campaign and status ok.
     */
    @PostMapping
    public ResponseEntity<?> addCampaign(@RequestBody Campaign campaign) {
        Campaign savedCampaign;
        savedCampaign = campaignService.saveCampaign(campaign);
        return new ResponseEntity<>(savedCampaign, HttpStatus.CREATED);
    }

    /**
     * The method update campaign.
     *
     * @param id       This is campaign's id which should be updated.
     * @param campaign This is new body for campaign which should be updated.
     * @return response with body of updated campaign and status ok.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCampaign(@PathVariable(value = "id") UUID id, @RequestBody Campaign campaign) {
        Campaign updatedCampaign;
        try {
            updatedCampaign = campaignService.updateCampaign(id, campaign);
        } catch (EntityNotExistException e) {
            log.error("IN CampaignController updateCampaign - id is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            log.error("IN CampaignController updateCampaign - campaign by id {} not found", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedCampaign, HttpStatus.OK);
    }

    /**
     * The method delete campaign.
     *
     * @param id This is campaign's id which should be deleted.
     * @return response status no_content.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<?> deleteCampaign(@PathVariable(value = "id") UUID id) {
        campaignService.deleteCampaignById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}