
package acme.features.member.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projectMember.ProjectMember;
import acme.entities.projects.Project;

@Repository
public interface MemberProjectRepository extends AbstractRepository {

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findProjectById(int id);

	@Query("SELECT p FROM Project p WHERE p in (SELECT pm.project FROM ProjectMember pm WHERE pm.member.id = :id)")
	Collection<Project> findProjectsByMemberId(int id);

	@Query("SELECT pm FROM ProjectMember pm WHERE pm.member.id = :memeberId AND pm.project.id = :projectId")
	Collection<ProjectMember> findProjectMemberByMemberIdAndProjectId(int memeberId, int projectId);
}
