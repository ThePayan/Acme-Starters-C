
package acme.features.manager.project;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.auditreport.AuditReport;
import acme.entities.campaign.Campaign;
import acme.entities.inventions.Invention;
import acme.entities.projectMember.ProjectMember;
import acme.entities.projects.Project;
import acme.entities.sponsorship.Sponsorship;
import acme.entities.strategies.Strategy;
import acme.features.auditor.auditreport.AuditorAuditReportRepository;
import acme.features.fundraiser.strategy.FundraiserStrategyRepository;
import acme.features.inventor.invention.InventorInventionRepository;
import acme.features.spokesperson.campaign.SpokespersonCampaignRepository;
import acme.features.sponsor.sponsorship.SponsorSponsorshipRepository;
import acme.realms.Manager;

@Service
public class ManagerProjectDeleteService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository		repository;
	@Autowired
	private FundraiserStrategyRepository	fundraiserRepository;
	@Autowired
	private InventorInventionRepository		inventorRepository;
	@Autowired
	private SponsorSponsorshipRepository	sponsorRepository;
	@Autowired
	private SpokespersonCampaignRepository	spokespersonRepository;
	@Autowired
	private AuditorAuditReportRepository	auditorRepository;
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
		;
	}

	@Override
	public void execute() {
		Collection<ProjectMember> pm = this.repository.findProjectMembersByProjectId(this.project.getId());
		List<Strategy> strategies = this.repository.findStrategiesByProjectId(this.project.getId());
		List<Campaign> campaigns = this.repository.findCampaignsByProjectId(this.project.getId());
		List<Invention> inventions = this.repository.findInventionsByProjectId(this.project.getId());
		List<Sponsorship> sponsorships = this.repository.findSponsorshipsByProjectId(this.project.getId());
		List<AuditReport> auditReports = this.repository.findAuditReportsByProjectId(this.project.getId());
		this.repository.deleteAll(pm);
		for (Strategy s : strategies) {
			s.setProject(null);
			this.fundraiserRepository.save(s);
		}
		for (Campaign c : campaigns) {
			c.setProject(null);
			this.spokespersonRepository.save(c);
		}
		for (Invention i : inventions) {
			i.setProject(null);
			this.inventorRepository.save(i);
		}
		for (Sponsorship ss : sponsorships) {
			ss.setProject(null);
			this.sponsorRepository.save(ss);
		}
		for (AuditReport au : auditReports) {
			au.setProject(null);
			this.auditorRepository.save(au);
		}
		this.repository.delete(this.project);
	}

	@Override
	public void unbind() {
		int ManagerId;
		Tuple tuple;
		ManagerId = super.getRequest().getPrincipal().getActiveRealm().getId();
		super.unbindObject(this.project, "title", "description", "kickOff", "closeOut", "keyWords", "draftMode");
	}

}
