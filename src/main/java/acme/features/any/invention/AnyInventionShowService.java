
package acme.features.any.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.datatypes.Money;
import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;

@Service
public class AnyInventionShowService extends AbstractService<Any, Invention> {

	@Autowired
	private AnyInventionRepository	repository;

	private Invention				invention;

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

		status = this.invention != null && !this.invention.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		double months = this.invention.getMonthsActive();
		int inventorId = this.invention.getInventor().getId();
		Money costs = this.invention.getCosts();
		tuple = super.unbindObject(this.invention, //
			"ticker", "startMoment", "endMoment", "name", //
			"description", "moreInfo");
		tuple.put("monthsActive", months);
		tuple.put("Costs", costs);
		tuple.put("inventorId", inventorId);
	}

}
