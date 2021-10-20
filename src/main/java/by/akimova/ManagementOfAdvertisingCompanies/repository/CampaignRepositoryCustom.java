package by.akimova.ManagementOfAdvertisingCompanies.repository;

import by.akimova.ManagementOfAdvertisingCompanies.model.Campaign;

import java.util.List;

public interface CampaignRepositoryCustom {
    List<Campaign> search(String name, String link, String geolocation);

}
