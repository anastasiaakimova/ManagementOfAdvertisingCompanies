package by.akimova.ManagementOfAdvertisingCompanies.service.impl;

import by.akimova.ManagementOfAdvertisingCompanies.exception.EntityNotExistException;
import by.akimova.ManagementOfAdvertisingCompanies.exception.EntityNotFoundException;
import by.akimova.ManagementOfAdvertisingCompanies.model.Campaign;
import by.akimova.ManagementOfAdvertisingCompanies.repository.CampaignRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Testing class for {@link CampaignServiceImpl}
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CampaignServiceImplTest {
    @Mock
    private CampaignRepository campaignRepository;

    @InjectMocks
    private CampaignServiceImpl campaignServiceImpl;

    private Campaign campaign1;
    private Campaign campaign2;
    private Campaign campaignToSave;
    private List<Campaign> campaigns;

    @BeforeEach
    public void setUp() {
        campaigns = new ArrayList<>();

        campaign1 = new Campaign();

        campaign1.setId(UUID.randomUUID());
        campaign1.setName("name");
        campaign1.setLink("www.company.com");
        campaign1.setIsActive(Boolean.TRUE);
        campaign1.setCountry("England");
        campaign1.setAge("20");
        campaign1.setLanguage("English");
        campaign1.setGeolocation("England, London");
        campaign1.setAdvertiser(UUID.randomUUID());


        campaigns.add(campaign1);

        campaign2 = new Campaign();

        campaign2.setId(UUID.randomUUID());
        campaign2.setName("name");
        campaign2.setLink("www.company.com");
        campaign2.setIsActive(Boolean.TRUE);
        campaign2.setCountry("England");
        campaign2.setAge("20");
        campaign2.setLanguage("English");
        campaign2.setGeolocation("England, London");
        campaign2.setAdvertiser(UUID.randomUUID());


        campaigns.add(campaign2);

        campaignToSave = new Campaign();
        campaignToSave.setName("name");
        campaignToSave.setLink("www.company.com");
        campaignToSave.setIsActive(Boolean.TRUE);
        campaignToSave.setCountry("England");
        campaignToSave.setAge("20");
        campaignToSave.setLanguage("English");
        campaignToSave.setGeolocation("England, London");
        campaignToSave.setAdvertiser(UUID.randomUUID());
    }

    @AfterEach
    public void tearDown() {
        campaign1 = campaign2 = campaignToSave = null;
        campaigns = null;
    }


    @Test
    void saveCampaign_success() {
        when(campaignRepository.save(any(Campaign.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
        Campaign savedCampaign = campaignServiceImpl.saveCampaign(campaignToSave);
        assertThat(savedCampaign.getName()).isNotNull();
        assertThat(savedCampaign.getId()).isSameAs(campaignToSave.getId());
        verify(campaignRepository, times(1)).save(campaignToSave);

    }

    @Test
    void getById_success() throws EntityNotExistException, EntityNotFoundException {
        when(campaignRepository.findCampaignById(campaign1.getId())).thenReturn(campaign1);
        assertThat(campaignServiceImpl.getById((campaign1.getId()))).isEqualTo(campaign1);

    }

    @Test
    void updateCampaign_success() throws EntityNotExistException, EntityNotFoundException {
        when(campaignRepository.findCampaignById(campaign1.getId())).thenReturn(campaign1);
        Campaign campaign = campaignServiceImpl.getById((campaign1.getId()));
        when(campaignRepository.save(any(Campaign.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
        Campaign updatedCampaign = campaignServiceImpl.updateCampaign(campaign.getId(), campaignToSave);
        assertThat(updatedCampaign.getName()).isEqualTo(campaignToSave.getName());

    }

}