
package acme.features.manager.projectmember;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projectMember.ProjectMember;
import acme.realms.Manager;

@Service
public class ManagerProjectMemberShowService extends AbstractService<Manager, ProjectMember> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectMemberRepository	repository;

	private ProjectMember					projectMember;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.projectMember = this.repository.findProjectMemberById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.projectMember != null && (!this.projectMember.getProject().getDraftMode() || this.projectMember.getProject().getManager().isPrincipal());
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.projectMember, "member.userAccount.username", "member.identity.fullName", "member.identity.email", "role");
	}

}
