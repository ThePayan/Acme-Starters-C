
package acme.features.member.invention;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.inventions.Invention;
import acme.entities.projectMember.ProjectMember;
import acme.entities.projects.Project;

@Repository
public interface MemberInventionRepository extends AbstractRepository {

	@Query("SELECT i FROM Invention i WHERE i.id = :id")
	Invention findInventionById(int id);

	@Query("SELECT i FROM Invention i WHERE i.project.id = :projectId")
	List<Invention> findInventionsByProjectId(int projectId);

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findProjectById(int id);

	@Query("SELECT pm FROM ProjectMember pm WHERE pm.project.id = :projectId AND pm.member.id = :memberId")
	Collection<ProjectMember> findProjectMembersByProjectIdAndMemberId(int memberId, int projectId);

}
