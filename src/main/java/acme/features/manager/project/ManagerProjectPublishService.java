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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.realms.Manager;

@Service
public class ManagerProjectPublishService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository	repository;

	private Project						project;

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
			boolean isBefore;
			isBefore = this.project.getKickOff().before(this.project.getCloseOut());
			super.state(isBefore, "*", "acme.validation.correctDates.message");
		}
		{
			boolean startFuture;
			startFuture = MomentHelper.isFuture(this.project.getKickOff());

			super.state(startFuture, "startMoment", "acme.validation.future-interval.message");
		}
		{
			Integer draftInventions = this.repository.countDraftInventions(this.project.getId());
			boolean allInventionsPublished = draftInventions == null || draftInventions == 0;
			super.state(allInventionsPublished, "*", "acme.validation.unpublished-inventions.message");
		}
		{

			Integer draftStrategies = this.repository.countDraftStrategies(this.project.getId());
			boolean allStrategiesPublished = draftStrategies == null || draftStrategies == 0;
			super.state(allStrategiesPublished, "*", "acme.validation.unpublished-strategies.message");
		}
		{
			Integer draftCampaigns = this.repository.countDraftCampaigns(this.project.getId());
			boolean allCampaignsPublished = draftCampaigns == null || draftCampaigns == 0;
			super.state(allCampaignsPublished, "*", "acme.validation.unpublished-campaigns.message");
		}
		{
			Integer draftSponsorships = this.repository.countDraftSponsorships(this.project.getId());
			boolean allSponsorshipsPublished = draftSponsorships == null || draftSponsorships == 0;
			super.state(allSponsorshipsPublished, "*", "acme.validation.unpublished-sponsorships.message");
		}
		{
			Integer draftAuditReports = this.repository.countDraftAuditReports(this.project.getId());
			boolean allAuditReportsPublished = draftAuditReports == null || draftAuditReports == 0;
			super.state(allAuditReportsPublished, "*", "acme.validation.unpublished-auditReports.message");
		}
	}

	@Override
	public void execute() {
		this.project.setDraftMode(false);
		this.repository.save(this.project);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.project, "title", "description", "kickOff", "closeOut", "keyWords", "draftMode");
	}

}
