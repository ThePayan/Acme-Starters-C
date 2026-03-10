
package acme.features.authenticated.inventions;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.inventions.Invention;

@Repository
public interface AuthenticatedInventionRepository extends AbstractRepository {

	@Query("select sum(p.cost.amount) from Part p where p.invention.id = :id and p.cost.currency = 'EUR'")
	Double getSumOfCosts(int id);

	@Query("SELECT COUNT(s) FROM Part s WHERE s.invention.id = :id")
	Integer getNumOfParts(@Param("id") int id);

	Invention findInventionByTicker(String ticker);
}
