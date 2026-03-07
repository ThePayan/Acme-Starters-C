
package acme.common.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.sponsorship.Sponsorship;
import acme.features.authenticated.sponsor.AuthenticatedSponsorRepository;

@Validator
public class SponsorshipValidator extends AbstractValidator<ValidSponsorship, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedSponsorRepository repository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidSponsorship annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Sponsorship sponsorship, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (sponsorship == null)
			result = true;
		else {
			boolean isDraft = sponsorship.getDraftMode();
			{
				boolean uniqueSponsorship;
				Sponsorship existingSponsorship = null;

				if (sponsorship.getTicker() != null)
					existingSponsorship = this.repository.findSponsorshipByTicker(sponsorship.getTicker());
				uniqueSponsorship = existingSponsorship == null || existingSponsorship.equals(sponsorship);

				super.state(context, uniqueSponsorship, "ticker", "acme.validation.duplicated-ticker-message");
			}
			{
				boolean correctNumberOfDonations = true;
				if (!isDraft) {
					Integer existingDonations = this.repository.getNumOfDonations(sponsorship.getId());
					correctNumberOfDonations = existingDonations != null && existingDonations >= 1;
				}
				super.state(context, correctNumberOfDonations, "*", "acme.validation.numberOfDonations");
			}
			{
				boolean correctDates = true;
				Date startMoment = sponsorship.getStartMoment();
				Date endMoment = sponsorship.getEndMoment();
				if (!isDraft && startMoment != null && endMoment != null)
					correctDates = MomentHelper.isBefore(startMoment, endMoment);
				super.state(context, correctDates, "endMoment", "acme.validation.correctDates.message");
			}
			result = !super.hasErrors(context);
		}
		return result;
	}

}
