
package acme.features.any.auditor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.realms.Auditor;

@Repository
public interface AnyAuditorRepository extends AbstractRepository {

	@Query("SELECT a FROM Auditor a WHERE a.id = :id")
	Auditor findAuditorById(int id);

	@Query("SELECT ua FROM Auditor a JOIN a.userAccount ua WHERE a.id = :id")
	UserAccount findUserAccountByAuditorId(int id);

	@Query("SELECT a FROM AuditReport ar JOIN ar.auditor a WHERE ar.id = :id")
	Auditor findAuditorByAuditReportId(int id);

}
