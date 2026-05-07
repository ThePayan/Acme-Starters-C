
package acme.features.any.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.inventions.Part;
import acme.entities.inventions.Partkind;

@Service
public class AnyPartShowService extends AbstractService<Any, Part> {

	@Autowired

	private AnyPartRepository	repository;

	private Part				part;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.part = this.repository.findById(id);
	}

	@Override
	public void authorise() {
		boolean status = this.part != null && !this.part.getInvention().getDraftMode();

		// Si el padre está en borrador pero el usuario ha iniciado sesión, comprobamos si es el Manager del proyecto
		if (!status && this.part != null && super.getRequest().getPrincipal() != null)
			if (this.part.getInvention().getProject() != null) {
				int principalId = super.getRequest().getPrincipal().getAccountId();
				int managerId = this.part.getInvention().getProject().getManager().getUserAccount().getId();
				status = principalId == managerId;
			}

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(Partkind.class, this.part.getKind());
		tuple = super.unbindObject(this.part, "name", "description", "cost");
		tuple.put("kind", choices);
	}

}
