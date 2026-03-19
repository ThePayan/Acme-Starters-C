
package acme.features.sponsor.donation;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.sponsorship.Donation;
import acme.realms.Sponsor;

@Controller
public class SponsorDonationController extends AbstractController<Sponsor, Donation> {

	// Constructors -----------------------------------------------------------

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", SponsorshipDonationListService.class);
		super.addBasicCommand("show", SponsorshipDonationShowService.class);
		super.addBasicCommand("create", SponsorshipDonationCreateService.class);
		super.addBasicCommand("update", SponsorshipDonationUpdateService.class);
		super.addBasicCommand("delete", SponsorshipDonationDeleteService.class);
	}

}
