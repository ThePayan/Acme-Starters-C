
package acme.features.sponsor.donation;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.sponsorship.Donation;
import acme.entities.sponsorship.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorshipDonationListService extends AbstractService<Sponsor, Donation> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorDonationRepository	repository;

	private Sponsorship					sponsorship;
	private Collection<Donation>		donations;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int sponsorshipId;

		sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		this.sponsorship = this.repository.findSponsorshipById(sponsorshipId);
		this.donations = this.repository.findDonationsBySponsorshipId(sponsorshipId);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.sponsorship != null && (!this.sponsorship.getDraftMode() || this.sponsorship.getSponsor().isPrincipal());

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		boolean showCreate;

		super.unbindObjects(this.donations, "name", "notes", "money", "kind");

		showCreate = this.sponsorship.getDraftMode() && this.sponsorship.getSponsor().isPrincipal();
		super.unbindGlobal("sponsorshipId", this.sponsorship.getId());
		super.unbindGlobal("showCreate", showCreate);
	}
}
