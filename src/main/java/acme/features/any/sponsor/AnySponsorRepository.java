
package acme.features.any.sponsor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.realms.Sponsor;

@Repository
public interface AnySponsorRepository extends AbstractRepository {

	@Query("SELECT s FROM Sponsor s WHERE s.id = :id")
	Sponsor findSponsorById(int id);

	@Query("SELECT ua FROM Sponsor s JOIN s.userAccount ua WHERE s.id = :id")
	UserAccount findUserAccountBySponsorId(int id);

	@Query("SELECT s FROM Sponsorship ss JOIN ss.sponsor s WHERE ss.id = :id")
	Sponsor findSponsorBySponsorshipId(int id);

}
