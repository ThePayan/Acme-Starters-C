
package acme.features.any.projectmember;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.projectMember.ProjectMember;
import acme.entities.projects.Project;

@Service
public class AnyProjectMemberListService extends AbstractService<Any, ProjectMember> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyProjectMemberRepository	repository;
	private Project						project;
	private Collection<ProjectMember>	projectMembers;

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
		status = this.project != null && !this.project.getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		for (ProjectMember projectMember : this.projectMembers) {
			Tuple tuple;

			tuple = super.unbindObject(projectMember, "role");
			tuple.put("member", projectMember.getMember().getIdentity().getFullName());
			tuple.put("username", projectMember.getMember().getUserAccount().getUsername());
			tuple.put("email", projectMember.getMember().getIdentity().getEmail());
		}
	}
}
