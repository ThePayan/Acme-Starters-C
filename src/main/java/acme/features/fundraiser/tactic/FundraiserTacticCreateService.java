/*
 * FundraiserTacticCreateService.java
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.Tactic;
import acme.entities.strategies.TacticKind;
import acme.realms.Fundraiser;

@Service
public class FundraiserTacticCreateService extends AbstractService<Fundraiser, Tactic> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private FundraiserTacticRepository	repository;

	private Tactic						tactic;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int strategyId;
		Strategy strategy;

		strategyId = super.getRequest().getData("strategyId", int.class);
		strategy = this.repository.findStrategyById(strategyId);

		this.tactic = super.newObject(Tactic.class);
		this.tactic.setName("");
		this.tactic.setNotes("");
		this.tactic.setExpectedPercentage(0.00);
		this.tactic.setStrategy(strategy);
	}

	@Override
	public void authorise() {
		boolean status;
		int strategyId;
		Strategy strategy;

		strategyId = super.getRequest().getData("strategyId", int.class);
		strategy = this.repository.findStrategyById(strategyId);
		status = strategy != null && //
			this.tactic.getStrategy().getDraftMode() && this.tactic.getStrategy().getFundraiser().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.tactic, "name", "notes", "expectedPercentage", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.tactic);
	}

	@Override
	public void execute() {
		this.repository.save(this.tactic);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		SelectChoices tacticKinds = SelectChoices.from(TacticKind.class, this.tactic.getKind());
		tuple = super.unbindObject(this.tactic, "name", "notes", "expectedPercentage");
		tuple.put("strategyId", this.tactic.getStrategy().getId());
		tuple.put("draftMode", this.tactic.getStrategy().getDraftMode());
		tuple.put("tacticKinds", tacticKinds);
	}

}
