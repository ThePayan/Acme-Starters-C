/*
 * AnySpokespersonShowService.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.any.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.campaign.Campaign;

@Service
public class AnyCampaignShowService extends AbstractService<Any, Campaign> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyCampaignRepository	repository;

	private Campaign				campaign;

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

		status = this.campaign != null && !this.campaign.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		double months = this.campaign.getMonthsActive();
		double effort = this.campaign.getEffort();
		tuple = super.unbindObject(this.campaign, //
			"ticker", "name", "description", "startMoment", //
			"endMoment", "moreInfo");
		tuple.put("monthsActive", months);
		tuple.put("efforts", effort);
		tuple.put("spokespersonId", this.campaign.getSpokesperson().getId());

	}

}
