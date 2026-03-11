
package acme.features.any.strategy;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
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
}
