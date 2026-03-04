
package acme.common.constraints;

import java.util.Date;

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

		// 2. Validación: Número de secciones
		Integer existingAuditSection = this.auditRepository.getNumberOfAuditSections(auditReport.getId());
		boolean correctNumberOfAuditSections = existingAuditSection != null && existingAuditSection >= 1;

		if (!correctNumberOfAuditSections && Boolean.FALSE.equals(auditReport.getDraftMode()))
			super.state(context, false, "draftMode", "acme.validation.numberOfAuditSections.message");

		// 3. Validación: Unicidad del Ticker
		AuditReport existingAuditReport = this.auditRepository.findAuditReportByTicker(auditReport.getTicker());
		boolean isUnique = existingAuditReport == null || existingAuditReport.equals(auditReport);
		super.state(context, isUnique, "ticker", "acme.validation.ticker.message");

		// 4. Validación: Fechas
		Date startMoment = auditReport.getStartMoment();
		Date endMoment = auditReport.getEndMoment();

		if (startMoment != null && endMoment != null && Boolean.FALSE.equals(auditReport.getDraftMode())) {
			boolean correctDates = MomentHelper.isBefore(startMoment, endMoment);
			if (!correctDates) {
				super.state(context, false, "endMoment", "acme.validation.correctDates.message");
				return false; // Salimos para no acumular más errores en el "*"
			}
		}
		return !super.hasErrors(context);
	}
}
