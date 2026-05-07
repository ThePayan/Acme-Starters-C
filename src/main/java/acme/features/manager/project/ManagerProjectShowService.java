
package acme.features.manager.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.realms.Manager;

@Service
public class ManagerProjectShowService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private ManagerProjectRepository	repository;

	private Project						project;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.project = this.repository.findProjectById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.project != null && (this.project.getManager().isPrincipal() || !this.project.getDraftMode());
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		double personMonths = this.project.getPersonMonths();
		tuple = super.unbindObject(this.project, "title", "description", "keyWords", "kickOff", "closeOut", "draftMode");
		tuple.put("personMonths", personMonths);
	}

}
