
package acme.features.member.strategy;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.strategies.Strategy;
import acme.realms.Member;

@Controller
public class MemberStrategyController extends AbstractController<Member, Strategy> {

	@PostConstruct
	protected void inicialise() {
		super.setMediaType(MediaType.TEXT_HTML);
		super.addBasicCommand("list", MemberStrategyListService.class);
		super.addBasicCommand("show", MemberStrategyShowService.class);
	}

}
