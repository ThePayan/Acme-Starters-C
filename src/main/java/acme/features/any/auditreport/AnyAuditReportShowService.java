
package acme.features.any.auditreport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.auditreport.AuditReport;

@Service
public class AnyAuditReportShowService extends AbstractService<Any, AuditReport> {

	@Autowired
	private AnyAuditReportRepository	repository;

	private AuditReport					auditReport;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.auditReport = this.repository.findAuditReportById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.auditReport != null && !this.auditReport.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		double months = this.auditReport.getMonthsActive();
		int hours = this.auditReport.getAllHours();
		tuple = super.unbindObject(this.auditReport, //
			"ticker", "startMoment", "endMoment", "name", //
			"description", "moreInfo");
		tuple.put("monthsActive", months);
		tuple.put("allHours", hours);
	}
}
