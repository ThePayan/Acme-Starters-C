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
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaign.Campaign;
import acme.entities.projectMember.ProjectMember;
import acme.entities.projectMember.Role;
import acme.entities.projects.Project;

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

	@Query("SELECT c FROM Campaign c WHERE c.project.id = :projectId")
	List<Campaign> findCampaignsByProjectId(int projectId);

	@Query("SELECT m.id FROM Member m WHERE m.userAccount.id = :id")
	int findMemberIdByUserAccountId(int id);

	@Query("SELECT pm FROM ProjectMember pm WHERE pm.role = :role AND pm.member.id = :memberId AND pm.project.id = :projectId")
	ProjectMember findProjectMemberByRoleAndMemberIdAndProjectId(Role role, int memberId, int projectId);

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findProjectById(int id);
}
