
package acme.common.constraints;

import java.util.Date;

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

		// 1. Si es null
		if (auditReport == null)
			return true;

		// 2. Validación: Número de secciones
		Integer existingAuditSection = this.auditRepository.getNumberOfAuditSections(auditReport.getId());
		boolean correctNumberOfAuditSections = existingAuditSection != null && existingAuditSection >= 1;

		if (!correctNumberOfAuditSections && !auditReport.getDraftMode())
			super.state(context, false, "*", "acme.validation.NumberOfAuditSections.message");

		// 3. Validación: Unicidad del Ticker
		AuditReport existingAuditReport = this.auditRepository.findAuditReportByTicker(auditReport.getTicker());
		boolean isUnique = existingAuditReport == null || existingAuditReport.getId() == auditReport.getId();
		super.state(context, isUnique, "ticker", "acme.validation.ticker.message");

		// 4. Validación: Fechas
		Date start = auditReport.getStartMoment();
		Date end = auditReport.getEndMoment();

		if (start != null && end != null && auditReport.getDraftMode()) {
			boolean isBefore = start.before(end);
			super.state(context, isBefore, "*", "acme.validation.correctDates.message");
		}

		return !super.hasErrors(context);
	}
}
