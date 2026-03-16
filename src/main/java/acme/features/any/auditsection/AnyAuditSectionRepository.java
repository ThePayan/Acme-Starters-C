
package acme.features.any.auditsection;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditreport.AuditReport;
import acme.entities.auditreport.AuditSection;

@Repository
public interface AnyAuditSectionRepository extends AbstractRepository {

	@Query("SELECT ausec FROM AuditSection ausec JOIN ausec.auditReport ar WHERE ar.id = :id")
	Collection<AuditSection> findAuditSectionByReportId(int id);

	@Query("SELECT ausec FROM AuditSection ausec WHERE ausec.id = :id")
	AuditSection findById(int id);

	@Query("SELECT ar FROM AuditSection ausec JOIN ausec.auditReport ar WHERE ar.id = :id")
	AuditReport findAuditReportById(int id);

}
