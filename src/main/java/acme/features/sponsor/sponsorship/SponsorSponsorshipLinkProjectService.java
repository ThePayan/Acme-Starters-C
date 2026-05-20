
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.datatypes.Money;
import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.sponsorship.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorSponsorshipLinkProjectService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository	repository;

	private Sponsorship						sponsorship;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.sponsorship = this.repository.findSponsorshipById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.sponsorship != null && !this.sponsorship.getDraftMode() && this.sponsorship.getSponsor().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "project");
	}

	@Override
	public void validate() {
		super.validateObject(this.sponsorship);
		{
			boolean correctNumberOfDonations;
			correctNumberOfDonations = this.repository.getNumberOfDonationsBySponsorshipId(this.sponsorship.getId()) >= 1;
			super.state(correctNumberOfDonations, "*", "acme.validation.numberOfDonations.message");
		}
	}

	@Override
	public void execute() {
		this.repository.save(this.sponsorship);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		Collection<Project> projects = this.repository.findPublishedProjects();
		choices = SelectChoices.from(projects, "title", this.sponsorship.getProject());

		double months = this.sponsorship.getMonthsActive();
		Money money = this.sponsorship.getTotalMoney();
		tuple = super.unbindObject(this.sponsorship, //
			"ticker", "startMoment", "endMoment", "name", //
			"description", "moreInfo", "draftMode");
		tuple.put("monthsActive", months);
		tuple.put("totalMoney", money);
		tuple.put("project", choices);
	}

}
