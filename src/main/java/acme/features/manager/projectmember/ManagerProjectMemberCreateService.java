
package acme.features.manager.projectmember;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.UserAccount;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.projectMember.ProjectMember;
import acme.entities.projectMember.Role;
import acme.entities.projects.Project;
import acme.realms.Manager;
import acme.realms.Member;

@Service
public class ManagerProjectMemberCreateService extends AbstractService<Manager, ProjectMember> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectMemberRepository	repository;

	private ProjectMember					projectMember;

	private Collection<Member>				members;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int projectId = super.getRequest().getData("projectId", int.class);
		Project project = this.repository.findProjectById(projectId);
		this.members = this.repository.findAllAvaliableMembersByProjectId(projectId);
		this.projectMember = super.newObject(ProjectMember.class);
		if (this.members != null)
			this.projectMember.setProject(project);
	}

	@Override
	public void authorise() {
		boolean status;
		int projectId;
		Project project;

		projectId = super.getRequest().getData("projectId", int.class);
		project = this.repository.findProjectById(projectId);
		status = project != null && project.getDraftMode() && project.getManager().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.projectMember, "member", "role");
	}

	@Override
	public void validate() {
		super.validateObject(this.projectMember);
		boolean possessesRole = false;
		{
			UserAccount account = this.projectMember.getMember().getUserAccount();
			if (account != null)
				possessesRole = account.hasRealmOfType(this.projectMember.getRole().getRealmClass());
			super.state(possessesRole, "role", "acme.validation.member-lacks-role");
		}
		{
			boolean uniqueProjectMember = false;
			ProjectMember existingProjectMember = null;
			existingProjectMember = this.repository.findByRoleAndMemberIdAndProjectId(this.projectMember.getRole(), this.projectMember.getMember().getId(), this.projectMember.getProject().getId());
			uniqueProjectMember = existingProjectMember == null || existingProjectMember.equals(this.projectMember);
			super.state(uniqueProjectMember, "*", "acme.validation.duplicated-member");
		}
	}

	@Override
	public void execute() {
		this.repository.save(this.projectMember);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		SelectChoices roles;
		SelectChoices membersChoice;

		roles = SelectChoices.from(Role.class, null);
		membersChoice = SelectChoices.from(this.members, "userAccount.username", null);

		tuple = super.unbindObject(this.projectMember, "member", "role");
		tuple.put("members", membersChoice);
		tuple.put("roles", roles);
		tuple.put("projectId", this.projectMember.getProject().getId());
	}
}
