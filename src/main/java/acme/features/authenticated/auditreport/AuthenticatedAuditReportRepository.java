
package acme.features.authenticated.auditreport;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AuthenticatedAuditReportRepository extends AbstractRepository {

	//COALESCE, sirve para el manejos de null a las peticiones SQL 
	@Query("SELECT SUM(COALESCE(ausec.hours, 0)) FROM AuditSection ausec WHERE ausec.auditReport.id = :id")
	public Integer getAllHours(int id);

	//Audit reports cannot be published unless they have at least one audit section.
	// TODO: Hacer su respectivo servicio I guess
	@Query("SELECT COUNT(s) FROM AuditSection s WHERE s.auditReport.id = :id")
	public Integer getNumberOfAuditSections(int id);

}
