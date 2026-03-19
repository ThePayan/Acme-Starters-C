/*
 * FundraiserTacticController.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.fundraiser.tactic;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.strategies.Tactic;
import acme.realms.Fundraiser;

@Controller
public class FundraiserTacticController extends AbstractController<Fundraiser, Tactic> {

	// Constructors -----------------------------------------------------------

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", FundraiserTacticListService.class);
		super.addBasicCommand("show", FundraiserTacticShowService.class);
		super.addBasicCommand("create", FundraiserTacticCreateService.class);
		super.addBasicCommand("update", FundraiserTacticUpdateService.class);
		super.addBasicCommand("delete", FundraiserTacticDeleteService.class);
	}

}
