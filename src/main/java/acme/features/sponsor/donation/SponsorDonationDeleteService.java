
package acme.features.sponsor.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.sponsorship.Donation;
import acme.realms.Sponsor;

@Service
public class SponsorDonationDeleteService extends AbstractService<Sponsor, Donation> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorDonationRepository	repository;

	private Donation					donation;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.donation = this.repository.findDonationById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.donation != null && //
			this.donation.getSponsorship().getDraftMode() && this.donation.getSponsorship().getSponsor().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.donation, "name", "notes", "money", "kind");
	}

	@Override
	public void validate() {
		;
	}

	@Override
	public void execute() {
		this.repository.delete(this.donation);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.donation, "name", "notes", "hours", "kind");
		tuple.put("sponsorshipId", this.donation.getSponsorship().getId());
		tuple.put("draftMode", this.donation.getSponsorship().getDraftMode());
	}
}
