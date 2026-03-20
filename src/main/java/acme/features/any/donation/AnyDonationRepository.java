
package acme.features.any.donation;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorship.Donation;
import acme.entities.sponsorship.Sponsorship;

@Repository
public interface AnyDonationRepository extends AbstractRepository {

	@Query("SELECT d FROM Donation d JOIN d.sponsorship ss WHERE ss.id = :id")
	Collection<Donation> findDonationsBySponsorshipId(int id);

	@Query("SELECT d FROM Donation d WHERE d.id = :id")
	Donation findById(int id);

	@Query("SELECT ss FROM Donation d JOIN d.sponsorship ss WHERE ss.id = :id")
	Sponsorship findSponsorshipById(int id);

}
