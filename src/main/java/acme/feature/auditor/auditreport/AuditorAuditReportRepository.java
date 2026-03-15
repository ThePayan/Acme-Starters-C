/*
 * AuditorAuditReportRepository.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.feature.auditor.auditreport;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditreport.AuditReport;
import acme.entities.auditreport.AuditSection;

@Repository
public interface AuditorAuditReportRepository extends AbstractRepository {

	@Query("select ar from AuditReport ar where ar.id = :id")
	AuditReport findAuditReportById(int id);

	@Query("select ar from AuditReport ar where ar.auditor.id = :auditorId")
	Collection<AuditReport> findAuditReportsByAuditorId(int auditorId);

	@Query("select ausec from AuditSection ausec where ausec.auditReport.id = :auditReportId")
	Collection<AuditSection> findAuditSectionsByAuditReportId(int auditReportId);

	@Query("SELECT COUNT(s) FROM AuditSection s WHERE s.auditReport.id = :id")
	Integer getNumberOfAuditSectionsByAuditReportId(int id);

}
