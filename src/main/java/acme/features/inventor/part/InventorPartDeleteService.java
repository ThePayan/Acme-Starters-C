
package acme.features.inventor.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.inventions.Part;
import acme.realms.Inventor;

@Service
public class InventorPartDeleteService extends AbstractService<Inventor, Part> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InventorPartRepository	repository;

	private Part					part;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.part = this.repository.findPartById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.part != null && //
			this.part.getInvention().getDraftMode() && this.part.getInvention().getInventor().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.part, "name", "description", "cost", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.part);
	}

	@Override
	public void execute() {
		this.repository.delete(this.part);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.part, "name", "description", "cost", "kind");
		tuple.put("inventionId", this.part.getInvention().getId());
		tuple.put("draftMode", this.part.getInvention().getDraftMode());
	}

}
