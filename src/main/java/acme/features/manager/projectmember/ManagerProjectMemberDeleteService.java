
package acme.features.manager.projectmember;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaign.Campaign;
import acme.entities.inventions.Invention;
import acme.entities.projectMember.ProjectMember;
import acme.entities.projects.Project;
import acme.entities.strategies.Strategy;
import acme.realms.Fundraiser;
import acme.realms.Inventor;
import acme.realms.Manager;
import acme.realms.Spokesperson;

@Service
public class ManagerProjectMemberDeleteService extends AbstractService<Manager, ProjectMember> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectMemberRepository	repository;
	private Project							project;
	private ProjectMember					projectMember;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int projectMemberId;

		projectMemberId = super.getRequest().getData("id", int.class);
		this.projectMember = this.repository.findProjectMemberById(projectMemberId);
		if (this.projectMember != null)
			this.project = this.projectMember.getProject();
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.projectMember != null && this.project.getDraftMode() && this.project.getManager().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.projectMember);
	}

	@Override
	public void validate() {
		;
	}
	@Override
	public void execute() {
		switch (this.projectMember.getRole()) {
		case FUNDRAISER: {
			Fundraiser fundraiser = this.repository.findFundraiserByUserAccountId(this.projectMember.getMember().getUserAccount().getId());
			Collection<Strategy> strategies = this.repository.findStrategyByFundraiserIdAndProjectId(fundraiser.getId(), this.project.getId());
			for (Strategy s : strategies) {
				s.setProject(null);
				this.repository.save(s);
			}
			break;
		}
		case INVENTOR: {
			Inventor inventor = this.repository.findInventorByUserAccountId(this.projectMember.getMember().getUserAccount().getId());
			Collection<Invention> inventions = this.repository.findInventionByInventorIdAndProjectId(inventor.getId(), this.project.getId());
			for (Invention i : inventions) {
				i.setProject(null);
				this.repository.save(i);
			}
			break;
		}
		case SPOKESPERSON: {
			Spokesperson spokesperson = this.repository.findSpokespersonByUserAccountId(this.projectMember.getMember().getUserAccount().getId());
			Collection<Campaign> campaigns = this.repository.findCampaignBySpokespersonIdAndProjectId(spokesperson.getId(), this.project.getId());
			for (Campaign c : campaigns) {
				c.setProject(null);
				this.repository.save(c);
			}
			break;
		}
		default: {
			break;
		}
		}
		this.repository.delete(this.projectMember);
	}
	@Override
	public void unbind() {
		super.unbindObject(this.projectMember, "member", "role");
		super.unbindGlobal("draftMode", this.project.getDraftMode());
	}
}
