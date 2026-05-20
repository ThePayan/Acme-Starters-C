
package acme.features.spokesperson.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.campaign.Campaign;
import acme.entities.projects.Project;
import acme.realms.Spokesperson;

@Service
public class SpokespersonCampaignPublishService extends AbstractService<Spokesperson, Campaign> {

	@Autowired
	private SpokespersonCampaignRepository	repository;

	private Campaign						campaign;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.campaign = this.repository.findCampaignById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.campaign != null && this.campaign.getDraftMode() && this.campaign.getSpokesperson().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.campaign);

		{
			boolean correctNumberOfAuditSections;
			correctNumberOfAuditSections = this.repository.getNumberOfMilestonesByACampaignId(this.campaign.getId()) >= 1;
			super.state(correctNumberOfAuditSections, "*", "acme.validation.numberOfMilestones.message");
		}
		{
			boolean isBefore = false;
			if (this.campaign.getStartMoment() != null && this.campaign.getEndMoment() != null)
				isBefore = this.campaign.getStartMoment().before(this.campaign.getEndMoment());
			super.state(isBefore, "*", "acme.validation.correctDates.message");
		}
	}

	@Override
	public void execute() {
		this.campaign.setDraftMode(false);
		this.repository.save(this.campaign);
	}

	@Override
	public void unbind() {
		SelectChoices choices;

		Collection<Project> projects = this.repository.findProjectsBySpokespersonId(this.campaign.getSpokesperson().getId());
		choices = SelectChoices.from(projects, "title", this.campaign.getProject());

		Tuple tuple;
		double months = this.campaign.getMonthsActive();
		Double effort = this.campaign.getEffort();
		tuple = super.unbindObject(this.campaign, //
			"ticker", "startMoment", "endMoment", "name", //
			"description", "moreInfo", "draftMode");
		tuple.put("monthsActive", months);
		tuple.put("efforts", effort);
		tuple.put("project", choices);
		tuple.put("projectDraftMode", this.campaign.getProject() != null ? this.campaign.getProject().getDraftMode() : true);
	}
}
