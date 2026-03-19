
package acme.common.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.auditreport.AuditReport;
import acme.features.any.auditreport.AnyAuditReportRepository;

@Validator
public class AuditValidator extends AbstractValidator<ValidAudit, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyAuditReportRepository auditRepository;

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
			boolean isDraft = auditReport.getDraftMode();
			{
				boolean uniqueAuditReport;
				AuditReport existingAuditReport = null;

				if (auditReport.getTicker() != null)
					existingAuditReport = this.auditRepository.findAuditReportByTicker(auditReport.getTicker());
				uniqueAuditReport = existingAuditReport == null || existingAuditReport.equals(auditReport);

				super.state(context, uniqueAuditReport, "ticker", "acme.validation.duplicated-ticker.message");
			}
			{
				boolean correctNumberOfAuditSections = true;
				if (!isDraft) {
					Integer existingAuditSections = this.auditRepository.getNumberOfAuditSections(auditReport.getId());
					correctNumberOfAuditSections = existingAuditSections != null && existingAuditSections >= 1;
				}
				super.state(context, correctNumberOfAuditSections, "*", "acme.validation.numberOfDonations");
			}
			{
				boolean correctDates = true;
				Date startMoment = auditReport.getStartMoment();
				Date endMoment = auditReport.getEndMoment();
				if (!isDraft && startMoment != null && endMoment != null)
					correctDates = MomentHelper.isBefore(startMoment, endMoment);
				super.state(context, correctDates, "endMoment", "acme.validation.correctDates.message");
			}
			result = !super.hasErrors(context);
		}
		return result;
	}
}
