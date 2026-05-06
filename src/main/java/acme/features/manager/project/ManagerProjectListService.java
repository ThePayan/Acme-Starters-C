
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.realms.Manager;

@Service
public class ManagerProjectListService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private ManagerProjectRepository	repository;
	private Collection<Project>			projects;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void load() {
		int managerId;
		managerId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.projects = this.repository.findProjectsByManagerId(managerId);
	}
	@Override
	public void unbind() {
		super.unbindObjects(this.projects, "title", "description", "keyWords", "kickOff", "closeOut", "draftMode");
	}

}
