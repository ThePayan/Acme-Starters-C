
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
			boolean isDraft = sponsorship.getDraftMode() != null && sponsorship.getDraftMode().booleanValue();
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
					Integer sponsorshipId = sponsorship.getId();
					Integer numDonations = this.repository.getNumOfDonations(sponsorshipId);

					if (numDonations == null)
						numDonations = 0;
					correctNumberOfDonations = numDonations > 0;
				}
				super.state(context, correctNumberOfDonations, "*", "acme.validation.numberOfDonations");
			}
			{
				boolean correctDates = true;
				if (!isDraft) {
					Date startMoment = sponsorship.getStartMoment();
					Date endMoment = sponsorship.getEndMoment();
					if (startMoment != null && endMoment != null)
						correctDates = MomentHelper.isBeforeOrEqual(startMoment, endMoment);
				}
				super.state(context, correctDates, "*", "acme.validation.correctDates.message");
			}
			result = !super.hasErrors(context);
		}
		return result;
	}

}
