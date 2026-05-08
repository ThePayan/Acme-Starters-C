
package acme.features.spokesperson.campaign;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaign.Campaign;
import acme.entities.campaign.Milestone;
import acme.entities.projects.Project;

@Repository
public interface SpokespersonCampaignRepository extends AbstractRepository {

	@Query("select c from Campaign c where c.id = :id")
	Campaign findCampaignById(int id);

	@Query("select c from Campaign c where c.spokesperson.id = :spokespersonId")
	Collection<Campaign> findCampaignsBySpokespersonId(int spokespersonId);

	@Query("select m from Milestone m where m.campaign.id = :campaignId")
	Collection<Milestone> findMilestonesByCampaignId(int campaignId);

	@Query("SELECT COUNT(m) FROM Milestone m WHERE m.campaign.id = :id")
	Integer getNumberOfMilestonesByACampaignId(int id);

	@Query("SELECT pm.project  FROM ProjectMember pm  WHERE pm.member.userAccount = (SELECT i.userAccount FROM Spokesperson i WHERE i.id = :spokespersonId) AND pm.project.draftMode = true")
	Collection<Project> findProjectsBySpokespersonId(int spokespersonId);
}
