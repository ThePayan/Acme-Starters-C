
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
public class SpokespersonCampaignLinkProjectService extends AbstractService<Spokesperson, Campaign> {
	// Internal state ---------------------------------------------------------

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

		status = this.campaign != null && this.campaign.getSpokesperson().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "project");
	}

	@Override
	public void validate() {
		super.validateObject(this.campaign);

	}

	@Override
	public void execute() {
		this.repository.save(this.campaign);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		Collection<Project> projects = this.repository.findProjectsByUserAccountId(this.campaign.getSpokesperson().getUserAccount().getId());
		choices = SelectChoices.from(projects, "title", this.campaign.getProject());

		tuple = super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
		tuple.put("project", choices);
	}
}
