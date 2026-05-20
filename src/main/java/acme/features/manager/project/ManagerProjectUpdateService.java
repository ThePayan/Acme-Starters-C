
package acme.features.manager.project;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.realms.Manager;

public class ManagerProjectUpdateService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository	repository;

	private Project						project;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.project = this.repository.findProjectById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.project != null && this.project.getDraftMode() && this.project.getManager().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.project, "title", "description", "kickOff", "closeOut", "keyWords");

	}

	@Override
	public void validate() {
		super.validateObject(this.project);
	}

	@Override
	public void execute() {
		this.repository.save(this.project);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		tuple = super.unbindObject(this.project, "title", "description", "kickOff", "closeOut", "keyWords", "draftMode");
		tuple.put("personMonths", this.project.getPersonMonths());
	}

}
