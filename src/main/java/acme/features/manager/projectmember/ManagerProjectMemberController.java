
package acme.features.manager.projectmember;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.projectMember.ProjectMember;
import acme.realms.Manager;

@Controller
public class ManagerProjectMemberController extends AbstractController<Manager, ProjectMember> {

	// Constructors -----------------------------------------------------------

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", ManagerProjectMemberListService.class);
		super.addBasicCommand("create", ManagerProjectMemberCreateService.class);
		super.addBasicCommand("delete", ManagerProjectMemberDeleteService.class);
		super.addBasicCommand("show", ManagerProjectMemberShowService.class);
	}

}
