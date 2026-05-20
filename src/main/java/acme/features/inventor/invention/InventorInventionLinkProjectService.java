
package acme.features.inventor.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.datatypes.Money;
import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.entities.projects.Project;
import acme.realms.Inventor;

@Service
public class InventorInventionLinkProjectService extends AbstractService<Inventor, Invention> {

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
		boolean projectDraftMode = true;
		if (this.invention != null && this.invention.getProject() != null)
			projectDraftMode = this.invention.getProject().getDraftMode();
		status = this.invention != null && projectDraftMode && this.invention.getInventor().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "project");
	}

	@Override
	public void validate() {
		super.validateObject(this.invention);
		{
			boolean linkToPubProject = true;
			if (this.invention.getProject() != null)
				linkToPubProject = this.invention.getProject().getDraftMode() || !this.invention.getDraftMode();
			super.state(linkToPubProject, "*", "acme.validation.link-pub-project");
		}
	}

	@Override
	public void execute() {
		this.repository.save(this.invention);
	}

	@Override
	public void unbind() {
		SelectChoices choices;

		Collection<Project> projects = this.repository.findProjectsByInventorId(this.invention.getInventor().getId());
		choices = SelectChoices.from(projects, "title", this.invention.getProject());

		Tuple tuple;
		double months = this.invention.getMonthsActive();
		Money costs = this.invention.getCosts();
		tuple = super.unbindObject(this.invention, //
			"ticker", "startMoment", "endMoment", "name", //
			"description", "moreInfo", "draftMode");
		tuple.put("monthsActive", months);
		tuple.put("Costs", costs);
		tuple.put("project", choices);
		tuple.put("projectDraftMode", this.invention.getProject() != null ? this.invention.getProject().getDraftMode() : true);
	}
}
