
package acme.features.member.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.projectMember.ProjectMember;
import acme.entities.projects.Project;
import acme.realms.Member;

@Service
public class MemberProjectShowService extends AbstractService<Member, Project> {

	@Autowired
	private MemberProjectRepository		repository;
	private Project						project;
	private Collection<ProjectMember>	projectMembers;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int projectId = super.getRequest().getData("id", int.class);
		int memberId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.project = this.repository.findProjectById(projectId);
		if (this.project != null)
			this.projectMembers = this.repository.findProjectMemberByMemberIdAndProjectId(memberId, projectId);
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.project != null && this.projectMembers.stream().anyMatch(x -> x.getProject().equals(this.project));
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		double personMonths = this.project.getPersonMonths();
		tuple = super.unbindObject(this.project, "title", "description", "keyWords", "kickOff", "closeOut");
		tuple.put("personMonths", personMonths);
	}

}
