
package acme.features.fundRaiser;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.strategies.Strategy;

@Repository
public interface StrategyRepository extends AbstractRepository {

	@Query("select sum(t.expectedPercentage) from Tactic t where t.strategy.id = :strategyId")
	Double getSumPercentages(int strategyId);

	@Query("select count(t) from Tactic t where t.strategy.id = :strategyId")
	Integer getNumOfTactics(int strategyId);

	@Query("select s from Strategy s where s.ticker = :ticker")
	Strategy findStrategyByTicker(String ticker);
}
