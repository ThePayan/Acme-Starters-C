
package acme.common.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.helpers.MomentHelper;
import acme.entities.project.Project;
import acme.features.any.project.AnyProjectRepository;

public class ProjectValidator extends AbstractValidator<ValidProject, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyProjectRepository repository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidProject annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Project project, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (project == null)
			result = true;
		else {
			boolean isDraft = project.getDraftMode() != null && project.getDraftMode().booleanValue();
			{
				boolean correctNumberOfInventions = true;
				if (!project.getDraftMode()) {
					Integer existingInventions;
					existingInventions = this.repository.getNumOfInventions(project.getId());
					if (existingInventions == null)
						existingInventions = 0;

					correctNumberOfInventions = existingInventions >= 1;
				}
				super.state(context, correctNumberOfInventions, "*", "acme.validation.numberOfInventions.message");
			}
			{
				boolean correctDates = true;

				if (!isDraft && project.getKickOff() != null && project.getCloseOut() != null)
					correctDates = MomentHelper.isBefore(project.getKickOff(), project.getCloseOut());
				super.state(context, correctDates, "kickOff", "acme.validation.correctDates.message");
				super.state(context, correctDates, "closeOut", "acme.validation.correctDates.message");
			}
			result = !super.hasErrors(context);
		}
		return result;
	}
}
