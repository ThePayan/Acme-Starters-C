
package acme.features.authenticated.inventions;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.inventions.Invention;

@Repository
public interface AuthenticatedInventionRepository extends AbstractRepository {

	@Query("select sum(p.cost.amount) from Part p where p.invention.id = :id and p.cost.currency = 'EUR'")
	public Double getSumOfCosts(int id);

	@Query("SELECT COUNT(s) FROM Invention s WHERE s.invention.id = :id")
	public Integer getNumberOfInventions(int id);

	public Invention findInventionByTicker(String ticker);
}
