
package acme.features.manager.projectmember;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projectMember.ProjectMember;
import acme.realms.Manager;
import acme.realms.Member;

@Service
public class ManagerProjectMemberShowService extends AbstractService<Manager, ProjectMember> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectMemberRepository	repository;

	private ProjectMember					projectMember;

	private Collection<Member>				members;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.projectMember = this.repository.findProjectMemberById(id);
		if (this.projectMember != null)
			this.members = this.repository.findMembersByProjectId(this.projectMember.getProject().getId());
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
