
package acme.common.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
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
			{
				boolean uniqueStrategy;
				Strategy existingStrategy;

				existingStrategy = this.repository.findStrategyByTicker(strategy.getTicker());
				uniqueStrategy = existingStrategy == null || existingStrategy.equals(strategy);

				super.state(context, uniqueStrategy, "ticker", "acme.validation.duplicated-ticker.message");
			}
			{
				boolean correctNumberOfTactics;
				Integer existingTactics;
				existingTactics = this.repository.getNumOfTactics(strategy.getId());
				correctNumberOfTactics = strategy.getDraftMode() || existingTactics >= 1;

				if (!correctNumberOfTactics)
					super.state(context, correctNumberOfTactics, "*", "acme.validation.numberOfTactics.message");
			}
			{
				boolean correctStartEndDate;
				correctStartEndDate = strategy.getEndMoment().after(strategy.getStartMoment()) && strategy.getStartMoment() != null && strategy.getEndMoment() != null;
				if (!correctStartEndDate)
					super.state(context, correctStartEndDate, "*", "acme.validation.correctDates.message");
			}
			result = !super.hasErrors(context);
		}

		return result;
	}

}
