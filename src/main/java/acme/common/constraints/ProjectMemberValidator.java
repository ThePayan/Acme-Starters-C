
package acme.common.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.principals.UserAccount;
import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.projectMember.ProjectMember;
import acme.features.any.projectmember.AnyProjectMemberRepository;

@Validator
public class ProjectMemberValidator extends AbstractValidator<ValidProjectMember, ProjectMember> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyProjectMemberRepository projectMemberRepository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidProjectMember annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final ProjectMember projectMember, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (projectMember == null)
			result = true;
		else {
			boolean possessesRole = false;
			{
				UserAccount account = null;

				if (projectMember.getMember() != null && projectMember.getRole() != null && projectMember.getProject() != null) {
					account = projectMember.getMember().getUserAccount();
					if (account != null)
						possessesRole = account.hasRealmOfType(projectMember.getRole().getRealmClass());
				}
				super.state(context, possessesRole, "role", "acme.validation.member-lacks-role");
			}
			{
				if (possessesRole) {
					boolean uniqueProjectMember = false;
					ProjectMember existingProjectMember = null;

					if (projectMember.getMember() != null && projectMember.getRole() != null && projectMember.getProject() != null)
						existingProjectMember = this.projectMemberRepository.findByRoleAndMemberIdAndProjectId(projectMember.getRole(), projectMember.getMember().getId(), projectMember.getProject().getId());
					uniqueProjectMember = existingProjectMember == null || existingProjectMember.equals(projectMember);

					super.state(context, uniqueProjectMember, "*", "acme.validation.duplicated-member");
				}
			}
			result = !super.hasErrors(context);
		}
		return result;
	}
}
