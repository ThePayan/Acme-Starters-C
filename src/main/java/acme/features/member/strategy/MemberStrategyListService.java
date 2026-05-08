
package acme.features.member.strategy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projectMember.ProjectMember;
import acme.entities.projects.Project;
import acme.entities.strategies.Strategy;
import acme.realms.Member;

@Service
public class MemberStrategyListService extends AbstractService<Member, Strategy> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private MemberStrategyRepository	repository;

	private Collection<Strategy>		strategies;

	private Project						project;

	private Collection<ProjectMember>	isMember;


	// AbstractService interface -------------------------------------------
	@Override
	public void load() {
		int memberId = super.getRequest().getPrincipal().getActiveRealm().getId();
		int projectId = super.getRequest().getData("projectId", int.class);
		this.isMember = this.repository.findProjectMembersByProjectIdAndMemberId(memberId, projectId);
		this.strategies = this.repository.findStrategiesByProjectId(projectId);
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
		super.unbindObjects(this.strategies, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "fundraiser.bank");
	}
}
