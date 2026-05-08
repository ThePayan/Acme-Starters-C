
package acme.features.member.projectmember;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projectMember.ProjectMember;
import acme.entities.projects.Project;
import acme.realms.Member;

@Service
public class MemberProjectMemberListService extends AbstractService<Member, ProjectMember> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private MemberProjectMemberRepository	repository;
	private Project							project;
	private Collection<ProjectMember>		isMember;
	private Collection<ProjectMember>		projectMembers;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int projectId;
		int memberId;
		memberId = super.getRequest().getPrincipal().getActiveRealm().getId();
		projectId = super.getRequest().getData("projectId", int.class);
		this.isMember = this.repository.findProjectMembersByProjectIdAndMemberId(memberId, projectId);
		this.project = this.repository.findProjectById(projectId);
		this.projectMembers = this.repository.findProjectMembersByProjectId(projectId);
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.project != null && !this.isMember.isEmpty();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.projectMembers, "member.userAccount.username", "member.identity.fullName", "member.identity.email", "role");
	}
}
