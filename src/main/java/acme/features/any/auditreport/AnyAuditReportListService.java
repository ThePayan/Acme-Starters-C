
package acme.features.any.auditreport;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.auditreport.AuditReport;
import acme.entities.projects.Project;

@Service
public class AnyAuditReportListService extends AbstractService<Any, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyAuditReportRepository	repository;

	private Collection<AuditReport>		auditReports;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		if (super.getRequest().hasData("projectId", int.class)) {
			int projectId = super.getRequest().getData("projectId", int.class);
			this.auditReports = this.repository.findAuditReportsByProjectId(projectId);
		} else
			this.auditReports = this.repository.findAllPublishedAuditReports();
	}

	@Override
	public void authorise() {
		boolean auth = true;
		if (super.getRequest().hasData("projectId", int.class)) {
			int projectId = super.getRequest().getData("projectId", int.class);
			Project project = this.repository.findProjectById(projectId);
			if (project == null || this.repository.findProjectById(projectId).getDraftMode())
				auth = false;
		}
		super.setAuthorised(auth);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.auditReports, //
			"ticker", "auditor.firm", "startMoment", "endMoment", "name", //
			"description", "moreInfo");
	}
}
