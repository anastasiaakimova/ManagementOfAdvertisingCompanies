package by.akimova.ManagementOfAdvertisingCompanies.repository.impl;

import by.akimova.ManagementOfAdvertisingCompanies.model.Campaign;
import by.akimova.ManagementOfAdvertisingCompanies.model.Campaign_;
import by.akimova.ManagementOfAdvertisingCompanies.repository.CampaignRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class CampaignRepositoryCustomImpl implements CampaignRepositoryCustom {

    @Autowired
    EntityManager entityManager;

    @Override
    public List<Campaign> search(String name, String link, String geolocation) {

        // create campaign query
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Campaign> cq = cb.createQuery(Campaign.class);
        Root<Campaign> campaign = cq.from(Campaign.class);

        // name predicate
        Predicate namePredicate = cb.like(campaign.get(Campaign_.NAME), "%" + name + "%");

        // link predicate
        Predicate linkPredicate = cb.like(campaign.get(Campaign_.LINK), "%" + link + "%");

        // geolocation predicate
        Predicate geolocationPredicate = cb.equal(campaign.get(Campaign_.GEOLOCATION), geolocation);

        // apply predicates
        cq.where(namePredicate, linkPredicate, geolocationPredicate);

        // return results
        TypedQuery<Campaign> query = entityManager.createQuery(cq);
        return query.getResultList();
    }
}
