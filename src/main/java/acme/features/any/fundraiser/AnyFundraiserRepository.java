
package acme.features.any.fundraiser;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.realms.Fundraiser;

@Repository
public interface AnyFundraiserRepository extends AbstractRepository {

	@Query("SELECT f FROM Fundraiser f WHERE f.id = :id")
	Fundraiser findFundraiserById(int id);

	@Query("SELECT ua FROM Fundraiser f JOIN f.userAccount ua WHERE f.id = :id")
	UserAccount findUserAccountByFundraiserId(int id);

	@Query("SELECT f FROM Strategy s JOIN s.fundraiser f WHERE s.id = :id")
	Fundraiser findFundraiserByStrategy(int id);
}
