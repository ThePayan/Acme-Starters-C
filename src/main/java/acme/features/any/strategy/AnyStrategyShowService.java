
package acme.features.any.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;

@Service
public class AnyStrategyShowService extends AbstractService<Any, Strategy> {

	@Autowired
	private AnyStrategyRepository	repository;
	private Strategy				strategy;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.strategy = this.repository.findStrategyById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.strategy != null && !this.strategy.getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		double months = this.strategy.getMonthsActive();
		double percentages = this.strategy.getExpectedPercentage();
		int fundraiserId = this.strategy.getFundraiser().getId();
		tuple = super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
		tuple.put("monthsActive", months);
		tuple.put("expectedPercentages", percentages);
		tuple.put("fundraiserId", fundraiserId);
	}

}
