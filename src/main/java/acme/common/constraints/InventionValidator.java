
package acme.common.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.inventions.Invention;
import acme.features.authenticated.inventions.AuthenticatedInventionRepository;

@Validator
public class InventionValidator extends AbstractValidator<ValidInvention, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedInventionRepository inventionRepository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidInvention annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Invention invention, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (invention == null)
			result = true;
		else {
			{
				boolean correctNumberOfParts;
				Integer existingParts;
				existingParts = this.inventionRepository.getNumOfParts(invention.getId());

				correctNumberOfParts = existingParts >= 1;

				if (!correctNumberOfParts && invention.getDraftMode())
					super.state(context, correctNumberOfParts, "*", "acme.validation.numberOfParts.message");

			}
			{
				boolean uniqueInvention;
				Invention existingInvention;

				existingInvention = this.inventionRepository.findInventionByTicker(invention.getTicker());
				uniqueInvention = existingInvention == null || existingInvention.equals(invention);

				super.state(context, uniqueInvention, "ticker", "acme.validation.ticker.message");
			}
			{
				boolean isAfter;

				Date startMoment = invention.getStartMoment();
				Date endMoment = invention.getEndMoment();
				if (startMoment != null || endMoment != null) {

					isAfter = startMoment.after(endMoment);

					if (isAfter && invention.getDraftMode())
						super.state(context, isAfter, "*", "acme.validation.correctDates.message");
				}

			}

			result = !super.hasErrors(context);
		}

		return result;
	}
}
