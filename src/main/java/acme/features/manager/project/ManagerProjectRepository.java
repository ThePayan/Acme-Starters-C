
package acme.features.manager.project;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditreport.AuditReport;
import acme.entities.campaign.Campaign;
import acme.entities.inventions.Invention;
import acme.entities.projectMember.ProjectMember;
import acme.entities.projects.Project;
import acme.entities.sponsorship.Sponsorship;
import acme.entities.strategies.Strategy;

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findProjectById(int id);

	@Query("SELECT p from Project p where p.manager.id = :managerId")
	Collection<Project> findProjectsByManagerId(int managerId);

	@Query("SELECT pm FROM ProjectMember pm WHERE pm.project.id = :projectId")
	Collection<ProjectMember> findProjectMembersByProjectId(int projectId);

	@Query("SELECT s FROM Strategy s WHERE s.project.id = :projectId")
	List<Strategy> findStrategiesByProjectId(int projectId);

	@Query("SELECT c FROM Campaign c WHERE c.project.id = :projectId")
	List<Campaign> findCampaignsByProjectId(int projectId);

	@Query("SELECT i FROM Invention i WHERE i.project.id = :projectId")
	List<Invention> findInventionsByProjectId(int projectId);

	@Query("SELECT ss FROM Sponsorship ss WHERE ss.project.id = :projectId")
	List<Sponsorship> findSponsorshipsByProjectId(int projectId);

	@Query("SELECT au FROM AuditReport au WHERE au.project.id = :projectId")
	List<AuditReport> findAuditReportsByProjectId(int projectId);

}
