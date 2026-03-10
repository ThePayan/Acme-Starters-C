
package acme.features.authenticated.sponsor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorship.Sponsorship;

@Repository
public interface AuthenticatedSponsorRepository extends AbstractRepository {

	@Query("select sum(d.money.amount) from Donation d where d.money.currency = 'EUR' AND d.sponsorship.id = :id")
	Double getTotalMoney(int id);

	@Query("select count(d) from Donation d where d.sponsorship.id = :id")
	Integer getNumOfDonations(int id);

	Sponsorship findSponsorshipByTicker(String ticker);
}
