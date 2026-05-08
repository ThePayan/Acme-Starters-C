
package acme.features.member.sponsorship;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.sponsorship.Sponsorship;
import acme.realms.Member;

@Controller
public class MemberSponsorshipController extends AbstractController<Member, Sponsorship> {

	// Constructors -----------------------------------------------------------

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", MemberSponsorshipListService.class);
		super.addBasicCommand("show", MemberSponsorshipShowService.class);
	}
}
