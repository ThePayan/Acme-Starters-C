
package acme.features.member.auditReport;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.auditreport.AuditReport;
import acme.realms.Member;

@Controller
public class MemberAuditReportController extends AbstractController<Member, AuditReport> {

	@PostConstruct
	protected void initialise() {

		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", MemberAuditReportListService.class);
		super.addBasicCommand("show", MemberAuditReportShowService.class);
	}
}
