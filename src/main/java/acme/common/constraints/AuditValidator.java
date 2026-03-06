
package acme.common.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
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

		// 1. Si es null
		if (auditReport == null)
			return true;
		else {
			{
				// 2. Validación: Número de secciones
				Integer existingAuditSection = this.auditRepository.getNumberOfAuditSections(auditReport.getId());
				boolean correctNumberOfAuditSections = existingAuditSection != null && existingAuditSection >= 1;

				if (!correctNumberOfAuditSections && Boolean.FALSE.equals(auditReport.getDraftMode()))
					super.state(context, false, "draftMode", "acme.validation.numberOfAuditSections.message");
			}
			{
				// 3. Validación: Unicidad del Ticker
				AuditReport existingAuditReport = this.auditRepository.findAuditReportByTicker(auditReport.getTicker());
				boolean isUnique = existingAuditReport == null || existingAuditReport.getId() == auditReport.getId();
				super.state(context, isUnique, "ticker", "acme.validation.ticker.message");
			}
			{
				// 4. Validación: Fechas
				{
					boolean correctDates = true;

					if (!auditReport.getDraftMode() && auditReport.getStartMoment() != null && auditReport.getEndMoment() != null)
						correctDates = MomentHelper.isBefore(auditReport.getStartMoment(), auditReport.getEndMoment());

					super.state(context, correctDates, "endMoment", "acme.validation.correctDates.message");
				}
			}
		}
		return !super.hasErrors(context);
	}
}
