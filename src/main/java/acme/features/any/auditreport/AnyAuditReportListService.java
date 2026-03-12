
package acme.features.any.auditreport;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.auditreport.AuditReport;

@Service
public class AnyAuditReportListService extends AbstractService<Any, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyAuditReportRepository	repository;

	private Collection<AuditReport>		auditReports;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		this.auditReports = this.repository.findAllPublishedAuditReports();
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.auditReports, //
			"ticker", "auditor.firm", "startMoment", "endMoment", "name", //
			"description", "moreInfo");
	}
}
