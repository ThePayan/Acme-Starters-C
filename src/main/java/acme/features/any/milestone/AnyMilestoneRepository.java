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

package acme.features.any.milestone;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaign.Campaign;
import acme.entities.campaign.Milestone;

@Repository
public interface AnyMilestoneRepository extends AbstractRepository {

	@Query("select c from Campaign c where c.id = :id")
	Campaign findCampaignById(int id);

	@Query("select m from Milestone m where m.id = :id")
	Milestone findMilestoneById(int id);

	@Query("SELECT m FROM Milestone m JOIN m.campaign c WHERE c.id = :id")
	Collection<Milestone> findMilestoneByCampaignId(int id);

}
