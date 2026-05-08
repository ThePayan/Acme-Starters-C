
package acme.features.member.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projectMember.ProjectMember;
import acme.entities.projects.Project;
import acme.entities.sponsorship.Sponsorship;
import acme.realms.Member;

@Service
public class MemberSponsorshipListService extends AbstractService<Member, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private MemberSponsorshipRepository	repository;

	private Collection<Sponsorship>		sponsorships;

	private Project						project;

	private Collection<ProjectMember>	isMember;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int memberId = super.getRequest().getPrincipal().getActiveRealm().getId();
		int projectId = super.getRequest().getData("projectId", int.class);
		this.isMember = this.repository.findProjectMembersByProjectIdAndMemberId(memberId, projectId);
		this.sponsorships = this.repository.findSponsorshipsByProjectId(projectId);
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
		super.unbindObjects(this.sponsorships, //
			"ticker", "name", "description", "startMoment", "endMoment", //
			"moreInfo", "sponsor.im");
	}

}
