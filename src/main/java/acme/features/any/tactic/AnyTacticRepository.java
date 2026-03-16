
package acme.features.any.tactic;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.Tactic;

@Repository
public interface AnyTacticRepository extends AbstractRepository {

	@Query("SELECT t FROM Tactic t JOIN t.strategy s WHERE s.id = :id")
	Collection<Tactic> findTacticByStrategyId(int id);

	@Query("SELECT t FROM Tactic t WHERE t.id = :id")
	Tactic findById(int id);

	@Query("SELECT s FROM Tactic t JOIN t.strategy s WHERE s.id = :id")
	Strategy findStrategyById(int id);
}
