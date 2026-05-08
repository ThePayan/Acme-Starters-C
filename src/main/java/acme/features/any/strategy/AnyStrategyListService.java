
package acme.features.any.strategy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.strategies.Strategy;

@Service
public class AnyStrategyListService extends AbstractService<Any, Strategy> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private AnyStrategyRepository	repository;
	private Collection<Strategy>	strategy;


	// AbstractService interface -------------------------------------------
	@Override
	public void load() {
		if (super.getRequest().hasData("projectId", int.class)) {
			int projectId = super.getRequest().getData("projectId", int.class);
			this.strategy = this.repository.findStrategiesByProjectId(projectId);
		} else
			this.strategy = this.repository.findAllPublishedStrategies();
	}
	@Override
	public void authorise() {
		boolean auth = true;
		if (super.getRequest().hasData("projectId", int.class)) {
			int projectId = super.getRequest().getData("projectId", int.class);
			Project project = this.repository.findProjectById(projectId);
			if (project == null || this.repository.findProjectById(projectId).getDraftMode() && !project.getManager().isPrincipal())
				auth = false;
		}
		super.setAuthorised(auth);
	}
	@Override
	public void unbind() {
		super.unbindObjects(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "fundraiser.bank");
	}
}
