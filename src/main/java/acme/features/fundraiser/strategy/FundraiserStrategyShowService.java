
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

	private Collection<Project>				projects;

	private Strategy						strategy;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int inventionId;

		inventionId = super.getRequest().getData("id", int.class);
		this.strategy = this.repository.findStrategyById(inventionId);
		this.projects = this.repository.findProjectsByUserAccountId(this.strategy.getFundraiser().getUserAccount().getId());
		if (this.strategy.getProject() != null)
			this.projects.add(this.strategy.getProject());
	}

	@Override
	public void authorise() {
		boolean status;

		int fundraiserId = this.repository.findFundraiserByAccountId(super.getRequest().getPrincipal().getAccountId());
		status = super.getRequest().getPrincipal().hasRealmOfType(Fundraiser.class) && this.strategy != null && this.strategy.getFundraiser().getId() == fundraiserId;
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		SelectChoices choices;

		choices = SelectChoices.from(this.projects, "title", this.strategy.getProject());
		Tuple tuple;
		double months = this.strategy.getMonthsActive();
		double expectedPercentage = this.strategy.getExpectedPercentage();
		tuple = super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
		tuple.put("monthsActive", months);
		tuple.put("expectedPercentage", expectedPercentage);
		tuple.put("project", choices);
	}

}
