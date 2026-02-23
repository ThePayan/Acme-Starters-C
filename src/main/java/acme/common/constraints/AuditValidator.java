
package acme.common.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.features.authenticated.auditreport.AuthenticatedAuditReportRepository;
import acme.realms.Auditor;

@Validator
public class AuditValidator extends AbstractValidator<ValidAudit, Auditor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedAuditReportRepository auditRepository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidAudit annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Auditor auditor, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (auditor == null)
			result = true;
		else {
			boolean correctNumberOfAuditSections;
			Integer existingAuditSection;
			existingAuditSection = this.auditRepository.getNumberOfAuditSections(auditor.getId());

			correctNumberOfAuditSections = existingAuditSection >= 1;

			if (correctNumberOfAuditSections)
				super.state(context, correctNumberOfAuditSections, "numberOfAuditSections", "acme.validation.NumberOfTactics.message");
		}
		result = !super.hasErrors(context);
		return result;
	}
}
