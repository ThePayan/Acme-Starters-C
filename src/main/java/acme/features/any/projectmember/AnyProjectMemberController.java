
package acme.features.any.projectmember;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.components.principals.Any;
import acme.client.controllers.AbstractController;
import acme.entities.projectMember.ProjectMember;

@Controller
public class AnyProjectMemberController extends AbstractController<Any, ProjectMember> {

	@PostConstruct
	protected void inicialise() {
		super.setMediaType(MediaType.TEXT_HTML);
		super.addBasicCommand("list", AnyProjectMemberListService.class);
	}
}
