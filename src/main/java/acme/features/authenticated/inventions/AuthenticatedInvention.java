
package acme.features.authenticated.inventions;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.datatypes.Money;
import acme.client.repositories.AbstractRepository;

@Repository
public interface AuthenticatedInvention extends AbstractRepository {

	@Query("select sum(p.cost.amount) from Part p where p.invention.id = :id")
	public Money getSumOfCosts(int id);

}
