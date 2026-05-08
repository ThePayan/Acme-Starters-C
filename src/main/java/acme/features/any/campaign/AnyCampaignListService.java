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

package acme.features.any.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.campaign.Campaign;
import acme.entities.projects.Project;

@Service
public class AnyCampaignListService extends AbstractService<Any, Campaign> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyCampaignRepository	repository;

	private Collection<Campaign>	campaigns;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		if (super.getRequest().hasData("projectId", int.class)) {
			int projectId = super.getRequest().getData("projectId", int.class);
			this.campaigns = this.repository.findCampaignsByProjectId(projectId);
		} else
			this.campaigns = this.repository.findCampaignByAvailability();
	}

	@Override
	public void authorise() {
		boolean auth = true;
		if (super.getRequest().hasData("projectId", int.class)) {
			int projectId = super.getRequest().getData("projectId", int.class);
			Project project = this.repository.findProjectById(projectId);
			if (project == null || this.repository.findProjectById(projectId).getDraftMode())
				auth = false;
		}
		super.setAuthorised(auth);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.campaigns, //
			"ticker", "name", "description", "startMoment", //
			"endMoment", "moreInfo", "spokesperson");
	}

}
