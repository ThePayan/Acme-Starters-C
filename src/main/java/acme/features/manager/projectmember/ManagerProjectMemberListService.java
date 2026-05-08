
package acme.features.manager.projectmember;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projectMember.ProjectMember;
import acme.entities.projects.Project;
import acme.realms.Manager;

@Service
public class ManagerProjectMemberListService extends AbstractService<Manager, ProjectMember> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectMemberRepository	repository;

	private Project							project;

	private Collection<ProjectMember>		projectMembers;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int projectId;

		projectId = super.getRequest().getData("projectId", int.class);
		this.project = this.repository.findProjectById(projectId);
		this.projectMembers = this.repository.findProjectMembersByProjectId(projectId);
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.project != null && (this.project.getManager().isPrincipal() || !this.project.getDraftMode());
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.projectMembers, "member.userAccount.username", "member.identity.fullName", "member.identity.email", "role");
		super.unbindGlobal("projectId", this.project.getId());
		super.unbindGlobal("draftMode", this.project.getDraftMode());
	}

}
