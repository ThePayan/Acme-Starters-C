
package acme.features.fundraiser.strategy;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.Tactic;

@Repository
public interface FundraiserStrategyRepository extends AbstractRepository {

	@Query("SELECT s FROM Strategy s WHERE s.id = :id")
	Strategy findStrategyById(int id);

	@Query("select s from Strategy s where s.fundraiser.id = :fundraiserId")
	Collection<Strategy> findStrategiesByFundraiserId(int fundraiserId);

	@Query("select t from Tactic t where t.strategy.id = :strategyId")
	Collection<Tactic> findTacticByStrategyId(int strategyId);

	@Query("select count(t) from Tactic t where t.strategy.id = :strategyId")
	Integer getNumOfTacticsByStrategyId(int strategyId);

	@Query("select sum(t.expectedPercentage) from Tactic t where t.strategy.id = :strategyId")
	Double getSumPercentages(int strategyId);

	@Query("select s from Strategy s where s.ticker = :ticker")
	Strategy findByTicker(@Param("ticker") String ticker);

	@Query("SELECT COUNT(s) > 0 FROM Strategy s WHERE s.ticker = :ticker AND s.id != :id ")
	boolean tickerExists(String ticker, int id);

}
