
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
public class SpokespersonCampaignShowService extends AbstractService<Spokesperson, Campaign> {

	@Autowired
	private SpokespersonCampaignRepository	repository;

	private Collection<Project>				projects;

	private Campaign						campaign;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int inventionId;

		inventionId = super.getRequest().getData("id", int.class);
		this.campaign = this.repository.findCampaignById(inventionId);
		this.projects = this.repository.findProjectsByUserAccountId(this.campaign.getSpokesperson().getUserAccount().getId());
		if (this.campaign.getProject() != null)
			this.projects.add(this.campaign.getProject());
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.campaign != null && this.campaign.getSpokesperson().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		SelectChoices choices;

		choices = SelectChoices.from(this.projects, "title", this.campaign.getProject());

		Tuple tuple;
		double months = this.campaign.getMonthsActive();
		Double effort = this.campaign.getEffort();
		tuple = super.unbindObject(this.campaign, //
			"ticker", "startMoment", "endMoment", "name", //
			"description", "moreInfo", "draftMode");
		tuple.put("monthsActive", months);
		tuple.put("efforts", effort);
		tuple.put("project", choices);
	}
}
