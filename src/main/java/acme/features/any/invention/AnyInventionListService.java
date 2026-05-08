
package acme.features.any.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.entities.projects.Project;

@Service
public class AnyInventionListService extends AbstractService<Any, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyInventionRepository	repository;

	private Collection<Invention>	inventions;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		if (super.getRequest().hasData("projectId", int.class)) {
			int projectId = super.getRequest().getData("projectId", int.class);
			this.inventions = this.repository.findInventionsByProjectId(projectId);
		} else
			this.inventions = this.repository.findAllPublishedInventions();
	}

	@Override
	public void authorise() {
		boolean auth = true;
		if (super.getRequest().hasData("projectId", int.class)) {
			int projectId = super.getRequest().getData("projectId", int.class);
			Project project = this.repository.findProjectById(projectId);
			if (project == null || this.repository.findProjectById(projectId).getDraftMode())
				auth = false;
		}
		super.setAuthorised(auth);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.inventions, //
			"ticker", "inventor.bio", "startMoment", "endMoment", "name", //
			"description", "moreInfo");
	}

}
