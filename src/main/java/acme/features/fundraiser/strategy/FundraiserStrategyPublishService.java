/*
 * ManagerProjectPublishService.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.fundraiser.strategy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.strategies.Strategy;
import acme.realms.Fundraiser;

@Service
public class FundraiserStrategyPublishService extends AbstractService<Fundraiser, Strategy> {

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
		status = this.strategy != null && this.strategy.getDraftMode() && this.strategy.getFundraiser().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.strategy);
		{
			boolean correctNumberOfTactics;
			correctNumberOfTactics = this.repository.getNumOfTacticsByStrategyId(this.strategy.getId()) >= 1;
			super.state(correctNumberOfTactics, "*", "acme.validation.numberOfTactics.message");
		}
		{
			boolean isBefore = false;
			if (this.strategy.getStartMoment() != null && this.strategy.getEndMoment() != null)
				isBefore = this.strategy.getStartMoment().before(this.strategy.getEndMoment());
			super.state(isBefore, "*", "acme.validation.correctDates.message");
		}
		{
			boolean startFuture = false;
			if (this.strategy.getStartMoment() != null)
				startFuture = MomentHelper.isFuture(this.strategy.getStartMoment());
			super.state(startFuture, "startMoment", "acme.validation.future-interval.message");
		}
		{
			boolean duplicated;

			duplicated = this.repository.tickerExists(this.strategy.getTicker(), this.strategy.getId());

			super.state(!duplicated, "ticker", "acme.validation.duplicated-ticker.message");
		}
	}

	@Override
	public void execute() {
		this.strategy.setDraftMode(false);
		this.repository.save(this.strategy);
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
