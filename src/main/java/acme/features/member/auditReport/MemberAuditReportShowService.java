
package acme.features.member.auditReport;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.auditreport.AuditReport;
import acme.entities.projectMember.ProjectMember;
import acme.entities.projects.Project;
import acme.realms.Member;

@Service
public class MemberAuditReportShowService extends AbstractService<Member, AuditReport> {

	@Autowired
	private MemberAuditReportRepository	repository;

	private AuditReport					auditReport;

	private Project						project;

	private Collection<ProjectMember>	isMember;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		int memberId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.auditReport = this.repository.findAuditReportById(id);
		if (this.auditReport != null)
			this.project = this.auditReport.getProject();
		if (this.project != null)
			this.isMember = this.repository.findProjectMembersByProjectIdAndMemberId(memberId, this.project.getId());
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.auditReport != null && this.project != null && this.isMember != null;
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		double months = this.auditReport.getMonthsActive();
		int auditorId = this.auditReport.getAuditor().getId();
		int hours = this.auditReport.getAllHours();
		tuple = super.unbindObject(this.auditReport, //
			"ticker", "startMoment", "endMoment", "name", //
			"description", "moreInfo");
		tuple.put("monthsActive", months);
		tuple.put("allHours", hours);
		tuple.put("auditorId", auditorId);
	}
}
