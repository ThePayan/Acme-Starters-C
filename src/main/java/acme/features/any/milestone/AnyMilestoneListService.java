/*
 * AnyMilestoneListService.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.any.milestone;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.campaign.Campaign;
import acme.entities.campaign.Milestone;

@Service
public class AnyMilestoneListService extends AbstractService<Any, Milestone> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyMilestoneRepository	repository;

	private Campaign				campaign;
	private Collection<Milestone>	milestone;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int campaignId;

		campaignId = super.getRequest().getData("campaignId", int.class);
		this.campaign = this.repository.findCampaignById(campaignId);
		this.milestone = this.repository.findMilestoneByCampaignId(campaignId);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.campaign != null && !this.campaign.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.milestone, //
			"title", "achievements", "effort", "kind");
	}

}
