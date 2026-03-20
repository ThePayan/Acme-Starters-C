
package acme.features.any.sponsorship;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorship.Sponsorship;

@Repository
public interface AnySponsorshipRepository extends AbstractRepository {

	@Query("select sum(d.money.amount) from Donation d where d.money.currency = 'EUR' AND d.sponsorship.id = :id")
	Double getTotalMoney(int id);

	@Query("select count(d) from Donation d where d.sponsorship.id = :id")
	Integer getNumOfDonations(int id);

	Sponsorship findSponsorshipByTicker(String ticker);

	@Query("Select s from Sponsorship s where s.draftMode = false")
	List<Sponsorship> findAllPublishedSponsorships();

	@Query("select s from Sponsorship s where s.id = :id")
	Sponsorship findSponsorshipById(int id);
}
