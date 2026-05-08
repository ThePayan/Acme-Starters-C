
package acme.features.member.invention;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.inventions.Invention;
import acme.realms.Member;

@Controller
public class MemberInventionController extends AbstractController<Member, Invention> {

	@PostConstruct
	protected void initialise() {

		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", MemberInventionListService.class);
		super.addBasicCommand("show", MemberInventionShowService.class);
	}

}
