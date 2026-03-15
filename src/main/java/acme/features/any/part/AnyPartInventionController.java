
package acme.features.any.part;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.components.principals.Any;
import acme.client.controllers.AbstractController;
import acme.entities.inventions.Part;
import acme.features.any.auditsection.AnyAuditSectionListService;
import acme.features.any.auditsection.AnyAuditSectionShowService;

@Controller
public class AnyPartInventionController extends AbstractController<Any, Part> {

	@PostConstruct
	protected void initialise() {

		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", AnyAuditSectionListService.class);
		super.addBasicCommand("show", AnyAuditSectionShowService.class);
	}
}
