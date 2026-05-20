
package acme.features.fundraiser.strategy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.strategies.Strategy;
import acme.realms.Fundraiser;

@Service
public class FundraiserStrategyShowService extends AbstractService<Fundraiser, Strategy> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private FundraiserStrategyRepository	repository;

	private Strategy						strategy;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.strategy = this.repository.findStrategyById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.strategy != null && this.strategy.getFundraiser().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		SelectChoices choices;

		Collection<Project> projects = this.repository.findProjectsByFundraiserId(this.strategy.getFundraiser().getId());
		choices = SelectChoices.from(projects, "title", this.strategy.getProject());

		Tuple tuple;
		double months = this.strategy.getMonthsActive();
		double expectedPercentage = this.strategy.getExpectedPercentage();
		tuple = super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
		tuple.put("monthsActive", months);
		tuple.put("expectedPercentage", expectedPercentage);
		tuple.put("project", choices);
		tuple.put("projectDraftMode", this.strategy.getProject() != null ? this.strategy.getProject().getDraftMode() : true);
	}

}
