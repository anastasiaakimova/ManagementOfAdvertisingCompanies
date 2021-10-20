package by.akimova.ManagementOfAdvertisingCompanies.service.impl;

import by.akimova.ManagementOfAdvertisingCompanies.exception.EntityNotExistException;
import by.akimova.ManagementOfAdvertisingCompanies.model.Advertiser;
import by.akimova.ManagementOfAdvertisingCompanies.repository.AdvertiserRepository;
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
 * Testing class for {@link AdvertiserServiceImpl}
 *
 * @author anastasiyaakimava
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AdvertiserServiceImplTest {
    @Mock
    private AdvertiserRepository advertiserRepository;

    @InjectMocks
    private AdvertiserServiceImpl advertiserServiceImpl;

    private Advertiser advertiser1;
    private Advertiser advertiser2;
    private Advertiser advertiserToSave;
    private List<Advertiser> advertisers;

    @BeforeEach
    public void setUp() {
        advertisers = new ArrayList<>();

        advertiser1 = new Advertiser();
        advertiser1.setId(UUID.randomUUID());
        advertiser1.setName("Alex");
        advertiser1.setDescription("asd@mail");
        advertiser1.setIsActive(Boolean.TRUE);
        advertiser1.setUsr(UUID.randomUUID());

        advertisers.add(advertiser1);

        advertiser2 = new Advertiser();

        advertiser2.setId(UUID.randomUUID());
        advertiser2.setName("Alex");
        advertiser2.setDescription("asd@mail");
        advertiser2.setIsActive(Boolean.TRUE);
        advertiser2.setUsr(UUID.randomUUID());

        advertisers.add(advertiser2);

        advertiserToSave = new Advertiser();
        advertiserToSave.setDescription("asd@mail");
        advertiserToSave.setName("name");
        advertiserToSave.setIsActive(Boolean.TRUE);
        advertiserToSave.setUsr(UUID.randomUUID());
    }

    @AfterEach
    public void tearDown() {
        advertiser1 = advertiser2 = advertiserToSave = null;
        advertisers = null;
    }

    @Test
    void saveAdvertiser_success() {
        when(advertiserRepository.save(any(Advertiser.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
        Advertiser savedAdvertiser = advertiserServiceImpl.saveAdvertiser(advertiserToSave);
        assertThat(savedAdvertiser.getName()).isNotNull();
        assertThat(savedAdvertiser.getId()).isSameAs(advertiserToSave.getId());
        verify(advertiserRepository, times(1)).save(advertiserToSave);

    }

    @Test
    void getById_success() throws EntityNotExistException {
        when(advertiserRepository.findAdvertiserById(advertiser1.getId())).thenReturn(advertiser1);
        assertThat(advertiserServiceImpl.getById((advertiser1.getId()))).isEqualTo(advertiser1);

    }

}