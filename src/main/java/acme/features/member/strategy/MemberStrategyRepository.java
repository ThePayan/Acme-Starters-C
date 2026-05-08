
package acme.features.member.strategy;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projectMember.ProjectMember;
import acme.entities.projects.Project;
import acme.entities.strategies.Strategy;

@Repository
public interface MemberStrategyRepository extends AbstractRepository {

	@Query("SELECT s FROM Strategy s WHERE s.id = :id")
	Strategy findStrategyById(int id);

	@Query("SELECT s FROM Strategy s WHERE s.project.id = :projectId")
	List<Strategy> findStrategiesByProjectId(int projectId);

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findProjectById(int id);

	@Query("SELECT pm FROM ProjectMember pm WHERE pm.project.id = :projectId AND pm.member.id = :memberId")
	Collection<ProjectMember> findProjectMembersByProjectIdAndMemberId(int memberId, int projectId);
}
