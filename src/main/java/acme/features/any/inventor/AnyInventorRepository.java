
package acme.features.any.inventor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.realms.Inventor;

@Repository
public interface AnyInventorRepository extends AbstractRepository {

	@Query("SELECT i FROM Inventor i WHERE i.id = :id")
	Inventor findInventorById(int id);

	@Query("SELECT ua FROM Inventor i JOIN i.userAccount ua WHERE i.id = :id")
	UserAccount findUserAccountByInventorId(int id);

	@Query("SELECT i FROM Invention inver JOIN inver.inventor i WHERE inver.id = :id")
	Inventor findInventorByInventionId(int id);

}
