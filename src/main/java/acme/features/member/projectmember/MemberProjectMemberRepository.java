
package acme.features.member.projectmember;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projectMember.ProjectMember;
import acme.entities.projectMember.Role;
import acme.entities.projects.Project;

@Repository
public interface MemberProjectMemberRepository extends AbstractRepository {

	@Query("SELECT pm FROM ProjectMember pm WHERE pm.role = :role AND pm.member.id = :memberId AND pm.project.id = :projectId")
	ProjectMember findByRoleAndMemberIdAndProjectId(Role role, int memberId, int projectId);

	@Query("SELECT pm FROM ProjectMember pm WHERE pm.project.id = :id")
	Collection<ProjectMember> findProjectMembersByProjectId(int id);

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findProjectById(int id);

	@Query("SELECT pm FROM ProjectMember pm WHERE pm.project.id = :projectId AND pm.member.id = :memberId")
	Collection<ProjectMember> findProjectMembersByProjectIdAndMemberId(int memberId, int projectId);
}
