
package acme.common.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.strategies.Strategy;
import acme.features.fundraiser.StrategyRepository;

@Validator
public class StrategyValidator extends AbstractValidator<ValidStrategy, Strategy> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private StrategyRepository repository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidStrategy annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Strategy strategy, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (strategy == null)
			result = true;
		else {
			boolean isDraft = strategy.getDraftMode() != null && strategy.getDraftMode().booleanValue();
			{
				boolean uniqueStrategy;
				Strategy existingStrategy;

				existingStrategy = this.repository.findStrategyByTicker(strategy.getTicker());
				uniqueStrategy = existingStrategy == null || existingStrategy.equals(strategy);

				super.state(context, uniqueStrategy, "ticker", "acme.validation.duplicated-ticker.message");
			}
			{
				boolean correctNumberOfTactics = true;
				if (!strategy.getDraftMode()) {
					Integer existingTactics;
					existingTactics = this.repository.getNumOfTactics(strategy.getId());
					if (existingTactics == null)
						existingTactics = 0;

					correctNumberOfTactics = existingTactics >= 1;
				}
				super.state(context, correctNumberOfTactics, "draftMode", "acme.validation.numberOfTactics.message");
			}
			{
				boolean correctDates = true;

				if (!isDraft && strategy.getStartMoment() != null && strategy.getEndMoment() != null)
					correctDates = MomentHelper.isBefore(strategy.getStartMoment(), strategy.getEndMoment());

				super.state(context, correctDates, "endMoment", "acme.validation.correctDates.message");
			}
			result = !super.hasErrors(context);
		}

		return result;
	}

}
