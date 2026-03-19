
package acme.common.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.inventions.Invention;
import acme.features.any.invention.AnyInventionRepository;

@Validator
public class InventionValidator extends AbstractValidator<ValidInvention, Invention> {

	@Autowired
	private AnyInventionRepository inventionRepository;


	@Override
	public boolean isValid(final Invention invention, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (invention == null)
			result = true;
		else {

			Boolean draftMode = invention.getDraftMode();

			{
				boolean uniqueInvention;
				Invention existingInvention = null;

				if (invention.getTicker() != null)
					existingInvention = this.inventionRepository.findInventionByTicker(invention.getTicker());

				uniqueInvention = existingInvention == null || existingInvention.getId() == invention.getId();

				super.state(context, uniqueInvention, "ticker", "acme.validation.ticker.message");
			}

			{
				boolean correctNumberOfParts;
				Integer existingParts = this.inventionRepository.getNumOfParts(invention.getId());

				if (existingParts == null)
					existingParts = 0;

				correctNumberOfParts = existingParts >= 1;

				if (!correctNumberOfParts && Boolean.FALSE.equals(draftMode))
					super.state(context, false, "*", "acme.validation.numberOfParts.message");
			}

			{
				Date startMoment = invention.getStartMoment();
				Date endMoment = invention.getEndMoment();

				if (startMoment != null && endMoment != null) {

					boolean isAfter = MomentHelper.isAfter(startMoment, endMoment);

					if (isAfter && Boolean.FALSE.equals(draftMode))
						super.state(context, false, "*", "acme.validation.correctDates.message");
				}
			}

			result = !super.hasErrors(context);
		}

		return result;
	}
}
