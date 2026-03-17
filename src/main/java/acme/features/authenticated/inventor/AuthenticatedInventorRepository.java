
package acme.features.authenticated.inventor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.realms.Inventor;

@Repository
public interface AuthenticatedInventorRepository extends AbstractRepository {

	@Query("select inven from Inventor inven where inven.userAccount.id = :id")
	Inventor findOneEmployerByUserAccountId(int id);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

}
