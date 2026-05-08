
package acme.features.member.projectmember;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.projectMember.ProjectMember;
import acme.realms.Member;

@Controller
public class MemberProjectMemberController extends AbstractController<Member, ProjectMember> {

	@PostConstruct
	protected void inicialise() {
		super.setMediaType(MediaType.TEXT_HTML);
		super.addBasicCommand("list", MemberProjectMemberListService.class);
	}
}
