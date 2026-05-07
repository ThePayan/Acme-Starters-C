
package acme.features.any.strategy;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projectMember.ProjectMember;
import acme.entities.projectMember.Role;
import acme.entities.strategies.Strategy;

@Repository
public interface AnyStrategyRepository extends AbstractRepository {

	@Query("select sum(t.expectedPercentage) from Tactic t where t.strategy.id = :strategyId")
	Double getSumPercentages(int strategyId);

	@Query("select count(t) from Tactic t where t.strategy.id = :strategyId")
	Integer getNumOfTactics(int strategyId);

	Strategy findStrategyByTicker(String ticker);

	@Query("SELECT s FROM Strategy s WHERE s.draftMode = false")
	List<Strategy> findAllPublishedStrategies();

	@Query("SELECT s FROM Strategy s WHERE s.id = :id")
	Strategy findStrategyById(int id);

	@Query("SELECT s FROM Strategy s WHERE s.project.id = :projectId")
	List<Strategy> findStrategiesByProjectId(int projectId);

	@Query("SELECT m.id FROM Member m WHERE m.userAccount.id = :id")
	int findMemberIdByUserAccountId(int id);

	@Query("SELECT pm FROM ProjectMember pm WHERE pm.role = :role AND pm.member.id = :memberId AND pm.project.id = :projectId")
	ProjectMember findProjectMemberByRoleAndMemberIdAndProjectId(Role role, int memberId, int projectId);
}
