
package acme.features.member.sponsorship;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projectMember.ProjectMember;
import acme.entities.projects.Project;
import acme.entities.sponsorship.Sponsorship;

@Repository
public interface MemberSponsorshipRepository extends AbstractRepository {

	@Query("select s from Sponsorship s where s.id = :id")
	Sponsorship findSponsorshipById(int id);

	@Query("SELECT s FROM Sponsorship s WHERE s.project.id = :projectId")
	List<Sponsorship> findSponsorshipsByProjectId(int projectId);

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findProjectById(int id);

	@Query("SELECT pm FROM ProjectMember pm WHERE pm.project.id = :projectId AND pm.member.id = :memberId")
	Collection<ProjectMember> findProjectMembersByProjectIdAndMemberId(int memberId, int projectId);
}
