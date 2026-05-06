
package acme.features.any.project;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaign.Campaign;
import acme.entities.inventions.Invention;
import acme.entities.projects.Project;
import acme.entities.strategies.Strategy;

@Repository
public interface AnyProjectRepository extends AbstractRepository {

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findProjectById(int id);

	@Query("SELECT p FROM Project p WHERE p.draftMode = false")
	List<Project> findAllPublishedProjects();

	@Query("select count(i) from Invention i where i.project.id = :projectId")
	Integer getNumOfInventions(int projectId);

	@Query("SELECT s FROM Strategy s WHERE s.project.id = :projectId")
	List<Strategy> findStrategiesByProjectId(int projectId);

	@Query("SELECT c FROM Campaign c WHERE c.project.id = :projectId")
	List<Campaign> findCampaignsByProjectId(int projectId);

	@Query("SELECT i FROM Invention i WHERE i.project.id = :projectId")
	List<Invention> findInventionsByProjectId(int projectId);

	@Query("SELECT COUNT(pm) FROM ProjectMember pm WHERE pm.project.id = :projectId")
	Integer getNumberOfMembersByProjectId(int projectId);

	@Query("SELECT COUNT(i) FROM Invention i WHERE i.project.id = :projectId AND i.draftMode = true")
	Integer countDraftInventions(int projectId);

	@Query("SELECT COUNT(s) FROM Strategy s WHERE s.project.id = :projectId AND s.draftMode = true")
	Integer countDraftStrategies(int projectId);

	@Query("SELECT COUNT(c) FROM Campaign c WHERE c.project.id = :projectId AND c.draftMode = true")
	Integer countDraftCampaigns(int projectId);

	@Query("SELECT COUNT(ss) FROM Sponsorship ss WHERE ss.project.id = :projectId AND ss.draftMode = true")
	Integer countDraftSponsorships(int projectId);

	@Query("SELECT COUNT(ar) FROM AuditReport ar WHERE ar.project.id = :projectId AND ar.draftMode = true")
	Integer countDraftAuditReports(int projectId);

}
