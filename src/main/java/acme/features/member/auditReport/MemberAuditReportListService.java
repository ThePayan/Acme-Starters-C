
package acme.features.member.auditReport;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.auditreport.AuditReport;
import acme.entities.projectMember.ProjectMember;
import acme.entities.projects.Project;
import acme.realms.Member;

@Service
public class MemberAuditReportListService extends AbstractService<Member, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private MemberAuditReportRepository	repository;

	private Collection<AuditReport>		auditReports;

	private Project						project;

	private Collection<ProjectMember>	isMember;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int memberId = super.getRequest().getPrincipal().getActiveRealm().getId();
		int projectId = super.getRequest().getData("projectId", int.class);
		this.isMember = this.repository.findProjectMembersByProjectIdAndMemberId(memberId, projectId);
		this.auditReports = this.repository.findAuditReportsByProjectId(projectId);
		this.project = this.repository.findProjectById(projectId);
	}

	@Override
	public void authorise() {
		Boolean status;
		status = this.project != null && !this.isMember.isEmpty();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.auditReports, //
			"ticker", "auditor.firm", "startMoment", "endMoment", "name", //
			"description", "moreInfo");
	}
}
