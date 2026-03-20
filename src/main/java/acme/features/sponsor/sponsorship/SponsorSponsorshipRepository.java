
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorship.Donation;
import acme.entities.sponsorship.Sponsorship;

@Repository
public interface SponsorSponsorshipRepository extends AbstractRepository {

	@Query("select ss from Sponsorship ss where ss.id = :id")
	Sponsorship findSponsorshipById(int id);

	@Query("select ss from Sponsorship ss where ss.sponsor.id = :sponsorId")
	Collection<Sponsorship> findSponsorshipsBySponsorId(int sponsorId);

	@Query("select d from Donation d where d.sponsorship.id = :sponsorshipId")
	Collection<Donation> findDonationsBySponsorshipId(int sponsorshipId);

	@Query("SELECT COUNT(d) FROM Donation d WHERE d.sponsorship.id = :id")
	Integer getNumberOfDonationsBySponsorshipId(int id);
}
