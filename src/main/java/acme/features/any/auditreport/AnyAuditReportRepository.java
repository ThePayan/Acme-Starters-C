
package acme.features.any.auditreport;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditreport.AuditReport;

@Repository
public interface AnyAuditReportRepository extends AbstractRepository {

	//COALESCE, sirve para el manejos de null a las peticiones SQL 
	@Query("SELECT SUM(COALESCE(ausec.hours, 0)) FROM AuditSection ausec WHERE ausec.auditReport.id = :id")
	Integer getAllHours(int id);

	//Audit reports cannot be published unless they have at least one audit section.
	@Query("SELECT COUNT(s) FROM AuditSection s WHERE s.auditReport.id = :id")
	Integer getNumberOfAuditSections(int id);

	AuditReport findAuditReportByTicker(String ticker);

	@Query("SELECT ar FROM AuditReport ar WHERE ar.draftMode = false")
	List<AuditReport> findAllPublishedAuditReports();

	@Query("SELECT ar FROM AuditReport ar WHERE ar.id = :id")
	AuditReport findAuditReportById(int id);

}
