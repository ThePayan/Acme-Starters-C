
package acme.features.member.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.realms.Member;

@Service
public class MemberProjectListService extends AbstractService<Member, Project> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private MemberProjectRepository	repository;
	private Collection<Project>		projects;


	// AbstractService interface -------------------------------------------
	@Override
	public void load() {
		int memberId;
		memberId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.projects = this.repository.findProjectsByMemberId(memberId);
	}
	@Override
	public void authorise() {
		super.setAuthorised(true);
	}
	@Override
	public void unbind() {
		super.unbindObjects(this.projects, "title", "description", "keyWords", "kickOff", "closeOut");
	}
}
