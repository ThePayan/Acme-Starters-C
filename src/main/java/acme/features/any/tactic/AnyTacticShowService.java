
package acme.features.any.tactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.strategies.Tactic;

@Service
public class AnyTacticShowService extends AbstractService<Any, Tactic> {

	@Autowired
	private AnyTacticRepository	repository;
	private Tactic				tactic;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.tactic = this.repository.findById(id);
	}

	@Override
	public void authorise() {
		boolean status = this.tactic != null && !this.tactic.getStrategy().getDraftMode();

		// Si el padre está en borrador pero el usuario ha iniciado sesión, comprobamos si es el Manager del proyecto
		if (!status && this.tactic != null && super.getRequest().getPrincipal() != null)
			if (this.tactic.getStrategy().getProject() != null) {
				int principalId = super.getRequest().getPrincipal().getAccountId();
				int managerId = this.tactic.getStrategy().getProject().getManager().getUserAccount().getId();
				status = principalId == managerId;
			}

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.tactic, //
			"name", "notes", "expectedPercentage", "kind");
	}
}
