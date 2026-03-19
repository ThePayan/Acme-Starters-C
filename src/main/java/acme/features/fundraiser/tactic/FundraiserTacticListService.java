/*
 * FundraiserTacticListService.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.fundraiser.tactic;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.Tactic;
import acme.realms.Fundraiser;

@Service
public class FundraiserTacticListService extends AbstractService<Fundraiser, Tactic> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private FundraiserTacticRepository	repository;
	private Strategy					strategy;
	private Collection<Tactic>			tactics;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int strategyId;

		strategyId = super.getRequest().getData("strategyId", int.class);
		this.strategy = this.repository.findStrategyById(strategyId);
		this.tactics = this.repository.findTacticsByStrategyId(strategyId);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.strategy != null && (!this.strategy.getDraftMode() || this.strategy.getFundraiser().isPrincipal());

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		boolean showCreate;

		super.unbindObjects(this.tactics, "name", "notes", "expectedPercentage", "kind");

		showCreate = this.strategy.getDraftMode() && this.strategy.getFundraiser().isPrincipal();
		super.unbindGlobal("strategyId", this.strategy.getId());
		super.unbindGlobal("showCreate", showCreate);
	}

}
