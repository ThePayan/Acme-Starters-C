
package acme.common.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.auditreport.AuditReport;
import acme.features.authenticated.auditreport.AuthenticatedAuditReportRepository;

@Validator
public class AuditValidator extends AbstractValidator<ValidAudit, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedAuditReportRepository auditRepository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidAudit annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final AuditReport auditReport, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (auditReport == null)
			result = true;
		else {
			boolean correctNumberOfAuditSections;
			Integer existingAuditSection;
			existingAuditSection = this.auditRepository.getNumberOfAuditSections(auditReport.getId());

			correctNumberOfAuditSections = existingAuditSection >= 1;

			if (correctNumberOfAuditSections && auditReport.getDraftMode() == true)
				super.state(context, correctNumberOfAuditSections, "*", "acme.validation.AuditSections.message");
		}
		result = !super.hasErrors(context);
		return result;
	}
}
