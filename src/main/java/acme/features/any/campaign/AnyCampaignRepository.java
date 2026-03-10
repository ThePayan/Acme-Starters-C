/*
 * AnySpokespersonRepository.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.any.campaign;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaign.Campaign;

@Repository
public interface AnyCampaignRepository extends AbstractRepository {

	@Query("select c from Campaign c where c.id = :id")
	Campaign findCampaignById(int id);

	@Query("select c from Campaign c where c.draftMode = false")
	Collection<Campaign> findCampaignByAvailability();

	@Query("select sum(m.effort) from Milestone m where m.campaign.id = :campaignId")
	Double getEfforts(int campaignId);

	@Query("select count(m) from Milestone m where m.campaign.id = :campaignId")
	Integer getNumOfMilestones(@Param("campaignId") int id);

	@Query("select c from Campaign c where c.ticker = :ticker")
	Campaign findCampaignByTicker(String ticker);

}
