
package acme.features.member.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.datatypes.Money;
import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.projectMember.ProjectMember;
import acme.entities.projects.Project;
import acme.entities.sponsorship.Sponsorship;
import acme.realms.Member;

@Service
public class MemberSponsorshipShowService extends AbstractService<Member, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private MemberSponsorshipRepository	repository;

	private Sponsorship					sponsorship;

	private Project						project;

	private Collection<ProjectMember>	isMember;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		int memberId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.sponsorship = this.repository.findSponsorshipById(id);
		if (this.sponsorship != null)
			this.project = this.sponsorship.getProject();
		if (this.project != null)
			this.isMember = this.repository.findProjectMembersByProjectIdAndMemberId(memberId, this.project.getId());
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.sponsorship != null && this.project != null && !this.isMember.isEmpty();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		double months = this.sponsorship.getMonthsActive();
		int sponsorId = this.sponsorship.getSponsor().getId();
		Money money = this.sponsorship.getTotalMoney();
		Tuple tuple;

		tuple = super.unbindObject(this.sponsorship,//
			"ticker", "name", "description",//
			"startMoment", "endMoment", "moreInfo");
		tuple.put("monthsActive", months);
		tuple.put("totalMoney", money);
		tuple.put("sponsorId", sponsorId);
	}

}
