
package acme.features.sponsor.donation;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorship.Donation;
import acme.entities.sponsorship.Sponsorship;

@Repository
public interface SponsorDonationRepository extends AbstractRepository {

	@Query("select ss from Sponsorship ss where ss.id = :sponsorshipId")
	Sponsorship findSponsorshipById(int sponsorshipId);

	@Query("select d from Donation d where d.id = :id")
	Donation findDonationById(int id);

	@Query("select d from Donation d where d.sponsorship.id = :sponsorshipId")
	Collection<Donation> findDonationsBySponsorshipId(int sponsorshipId);

}
