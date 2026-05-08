
package acme.features.member.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.entities.projectMember.ProjectMember;
import acme.entities.projects.Project;
import acme.realms.Member;

@Service
public class MemberInventionListService extends AbstractService<Member, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private MemberInventionRepository	repository;

	private Collection<Invention>		inventions;

	private Project						project;

	private Collection<ProjectMember>	isMember;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int memberId = super.getRequest().getPrincipal().getActiveRealm().getId();
		int projectId = super.getRequest().getData("projectId", int.class);
		this.isMember = this.repository.findProjectMembersByProjectIdAndMemberId(memberId, projectId);
		this.inventions = this.repository.findInventionsByProjectId(projectId);
		this.project = this.repository.findProjectById(projectId);
	}

	@Override
	public void authorise() {
		Boolean status;
		status = this.project != null && !this.isMember.isEmpty();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.inventions, //
			"ticker", "inventor.bio", "startMoment", "endMoment", "name", //
			"description", "moreInfo");
	}

}
