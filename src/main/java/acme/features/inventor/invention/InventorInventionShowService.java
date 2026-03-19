
package acme.features.inventor.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.datatypes.Money;
import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.realms.Inventor;

@Service
public class InventorInventionShowService extends AbstractService<Inventor, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InventorInventionRepository	repository;

	private Invention					invention;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.invention = this.repository.findInventionById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.invention != null && //
			(this.invention.getInventor().isPrincipal() || !this.invention.getDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		double months = this.invention.getMonthsActive();
		Money costs = this.invention.getCosts();
		tuple = super.unbindObject(this.invention, //
			"ticker", "startMoment", "endMoment", "name", //
			"description", "moreInfo", "draftMode");
		tuple.put("monthsActive", months);
		tuple.put("Costs", costs);
	}

}
