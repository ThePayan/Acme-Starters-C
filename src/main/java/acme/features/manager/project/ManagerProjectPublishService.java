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

package acme.features.manager.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.campaign.Campaign;
import acme.entities.inventions.Invention;
import acme.entities.projects.Project;
import acme.entities.strategies.Strategy;
import acme.features.fundraiser.strategy.FundraiserStrategyRepository;
import acme.features.inventor.invention.InventorInventionRepository;
import acme.features.spokesperson.campaign.SpokespersonCampaignRepository;
import acme.realms.Manager;

@Service
public class ManagerProjectPublishService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository		repository;
	@Autowired
	private FundraiserStrategyRepository	fundraiserRepository;
	@Autowired
	private InventorInventionRepository		inventorRepository;
	@Autowired
	private SpokespersonCampaignRepository	spokespersonRepository;

	private Project							project;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.project = this.repository.findProjectById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.project != null && this.project.getDraftMode() && this.project.getManager().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.project, "title", "description", "kickOff", "closeOut", "keyWords");
	}

	@Override
	public void validate() {
		super.validateObject(this.project);
		{
			boolean correctNumberOfInventions;
			correctNumberOfInventions = this.repository.getNumOfInventions(this.project.getId()) >= 1;
			super.state(correctNumberOfInventions, "*", "acme.validation.numberOfInventions.message");
		}
		{
			boolean isBefore = false;
			if (this.project != null && this.project.getKickOff() != null && this.project.getCloseOut() != null)
				isBefore = MomentHelper.isBefore(this.project.getKickOff(), this.project.getCloseOut());
			super.state(isBefore, "*", "acme.validation.correctDates.message");
		}
		{
			boolean startFuture = false;
			if (this.project != null && this.project.getKickOff() != null)
				startFuture = MomentHelper.isFuture(this.project.getKickOff());
			super.state(startFuture, "startMoment", "acme.validation.future-interval.message");
		}
		List<Strategy> strategies = this.repository.findStrategiesByProjectId(this.project.getId());
		for (Strategy s : strategies) {
			boolean correctNumberOfTactics;
			correctNumberOfTactics = this.fundraiserRepository.getNumOfTacticsByStrategyId(s.getId()) >= 1;
			super.state(correctNumberOfTactics, "*", "acme.validation.numberOfTactics.message");
		}
		List<Campaign> campaigns = this.repository.findCampaignsByProjectId(this.project.getId());
		for (Campaign c : campaigns) {
			boolean correctNumberOfMilestones;
			correctNumberOfMilestones = this.spokespersonRepository.getNumberOfMilestonesByACampaignId(c.getId()) >= 1;
			super.state(correctNumberOfMilestones, "*", "acme.validation.numberOfMilestones.message");
		}
		List<Invention> inventions = this.repository.findInventionsByProjectId(this.project.getId());
		for (Invention i : inventions) {
			boolean correctNumberOfParts;
			correctNumberOfParts = this.inventorRepository.getNumberOfPartsByInventionId(i.getId()) >= 1;
			super.state(correctNumberOfParts, "*", "acme.validation.numberOfParts.message");
		}
	}

	@Override
	public void execute() {
		this.project.setDraftMode(false);
		this.repository.save(this.project);
		List<Invention> inventions = this.repository.findInventionsByProjectId(this.project.getId());
		for (Invention i : inventions) {
			i.setDraftMode(false);
			this.inventorRepository.save(i);
		}
		List<Strategy> strategies = this.repository.findStrategiesByProjectId(this.project.getId());
		for (Strategy s : strategies) {
			s.setDraftMode(false);
			this.fundraiserRepository.save(s);
		}
		List<Campaign> campaigns = this.repository.findCampaignsByProjectId(this.project.getId());
		for (Campaign c : campaigns) {
			c.setDraftMode(false);
			this.spokespersonRepository.save(c);
		}
	}

	@Override
	public void unbind() {
		super.unbindObject(this.project, "title", "description", "kickOff", "closeOut", "keyWords", "draftMode");
	}

}
