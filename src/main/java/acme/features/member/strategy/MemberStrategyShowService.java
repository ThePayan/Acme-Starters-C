
package acme.features.member.strategy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.projectMember.ProjectMember;
import acme.entities.projects.Project;
import acme.entities.strategies.Strategy;
import acme.realms.Member;

@Service
public class MemberStrategyShowService extends AbstractService<Member, Strategy> {

	@Autowired
	private MemberStrategyRepository	repository;

	private Strategy					strategy;

	private Project						project;

	private Collection<ProjectMember>	isMember;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		int memberId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.strategy = this.repository.findStrategyById(id);
		if (this.strategy != null)
			this.project = this.strategy.getProject();
		if (this.project != null)
			this.isMember = this.repository.findProjectMembersByProjectIdAndMemberId(memberId, this.project.getId());
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.strategy != null && this.project != null && !this.isMember.isEmpty();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		double months = this.strategy.getMonthsActive();
		double percentages = this.strategy.getExpectedPercentage();
		int fundraiserId = this.strategy.getFundraiser().getId();
		tuple = super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
		tuple.put("monthsActive", months);
		tuple.put("expectedPercentages", percentages);
		tuple.put("fundraiserId", fundraiserId);
	}

}
