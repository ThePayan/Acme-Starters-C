/*
 * AuditorAuditSectionController.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.auditor.auditsection;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.auditreport.AuditSection;
import acme.realms.Auditor;

@Controller
public class AuditorAuditSectionController extends AbstractController<Auditor, AuditSection> {

	// Constructors -----------------------------------------------------------

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", AuditorAuditSectionListService.class);
		super.addBasicCommand("show", AuditorAuditSectionShowService.class);
		super.addBasicCommand("create", AuditorAuditSectionCreateService.class);
		super.addBasicCommand("update", AuditorAuditSectionUpdateService.class);
		super.addBasicCommand("delete", AuditorAuditSectionDeleteService.class);
	}

}
