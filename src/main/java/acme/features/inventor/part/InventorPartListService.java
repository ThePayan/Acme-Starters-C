
package acme.features.inventor.part;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.entities.inventions.Part;
import acme.realms.Inventor;

@Service
public class InventorPartListService extends AbstractService<Inventor, Part> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InventorPartRepository	repository;

	private Invention				invention;
	private Collection<Part>		parts;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int inventionId;

		inventionId = super.getRequest().getData("inventionId", int.class);
		this.invention = this.repository.findInventionById(inventionId);
		this.parts = this.repository.findPartsByInventionId(inventionId);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.invention != null && (!this.invention.getDraftMode() || this.invention.getInventor().isPrincipal());

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		boolean showCreate;

		super.unbindObjects(this.parts, "name", "description", "cost", "kind");

		showCreate = this.invention.getDraftMode() && this.invention.getInventor().isPrincipal();
		super.unbindGlobal("inventorId", this.invention.getId());
		super.unbindGlobal("showCreate", showCreate);
	}

}
