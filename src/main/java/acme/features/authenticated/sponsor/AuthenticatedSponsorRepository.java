
package acme.features.authenticated.sponsor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AuthenticatedSponsorRepository extends AbstractRepository {

	@Query("select sum(d.money.amount) from Donation d where d.money.currency = 'EUR' AND d.sponsorship.id = :id")
	public Double getTotalMoney(int id);
}
