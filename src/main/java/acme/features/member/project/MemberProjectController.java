
package acme.features.member.project;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.projects.Project;
import acme.realms.Member;

@Controller
public class MemberProjectController extends AbstractController<Member, Project> {

	@PostConstruct
	protected void inicialise() {
		super.setMediaType(MediaType.TEXT_HTML);
		super.addBasicCommand("list", MemberProjectListService.class);
		super.addBasicCommand("show", MemberProjectShowService.class);
	}

}
