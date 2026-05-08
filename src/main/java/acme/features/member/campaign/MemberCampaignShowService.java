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

package acme.features.member.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.campaign.Campaign;
import acme.entities.projectMember.ProjectMember;
import acme.entities.projects.Project;
import acme.realms.Member;

@Service
public class MemberCampaignShowService extends AbstractService<Member, Campaign> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private MemberCampaignRepository	repository;

	private Campaign					campaign;

	private Project						project;

	private Collection<ProjectMember>	isMember;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		int memberId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.campaign = this.repository.findCampaignById(id);
		if (this.campaign != null)
			this.project = this.campaign.getProject();
		if (this.project != null)
			this.isMember = this.repository.findProjectMembersByProjectIdAndMemberId(memberId, this.project.getId());
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.campaign != null && this.project != null && !this.isMember.isEmpty();
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
