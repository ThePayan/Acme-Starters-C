
package acme.features.member.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.datatypes.Money;
import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.entities.projectMember.ProjectMember;
import acme.entities.projects.Project;
import acme.realms.Member;

@Service
public class MemberInventionShowService extends AbstractService<Member, Invention> {

	@Autowired
	private MemberInventionRepository	repository;

	private Invention					invention;

	private Project						project;

	private Collection<ProjectMember>	isMember;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		int memberId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.invention = this.repository.findInventionById(id);
		if (this.invention != null)
			this.project = this.invention.getProject();
		if (this.project != null)
			this.isMember = this.repository.findProjectMembersByProjectIdAndMemberId(memberId, this.project.getId());
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.invention != null && this.project != null && this.isMember != null;
		super.setAuthorised(status);

	}

	@Override
	public void unbind() {
		Tuple tuple;
		double months = this.invention.getMonthsActive();
		int inventorId = this.invention.getInventor().getId();
		Money costs = this.invention.getCosts();
		tuple = super.unbindObject(this.invention, //
			"ticker", "startMoment", "endMoment", "name", //
			"description", "moreInfo");
		tuple.put("monthsActive", months);
		tuple.put("Costs", costs);
		tuple.put("inventorId", inventorId);
	}

}
