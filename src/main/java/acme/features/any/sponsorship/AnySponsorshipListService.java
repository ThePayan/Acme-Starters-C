
package acme.features.any.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.sponsorship.Sponsorship;

@Service
public class AnySponsorshipListService extends AbstractService<Any, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnySponsorshipRepository	repository;

	private Collection<Sponsorship>		sponsorships;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		if (super.getRequest().hasData("projectId", int.class)) {
			int projectId = super.getRequest().getData("projectId", int.class);
			this.sponsorships = this.repository.findSponsorshipsByProjectId(projectId);
		} else
			this.sponsorships = this.repository.findAllPublishedSponsorships();
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
		super.unbindObjects(this.sponsorships, //
			"ticker", "name", "description", "startMoment", "endMoment", //
			"moreInfo", "sponsor.im");
	}

}
