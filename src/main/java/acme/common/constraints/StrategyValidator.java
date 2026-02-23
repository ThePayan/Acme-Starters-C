
package acme.common.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.strategies.Strategy;
import acme.features.fundRaiser.StrategyRepository;

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
				boolean correctNumberOfTactics;
				Integer existingTactics;
				existingTactics = this.repository.getNumOfTactics(strategy.getId());
				correctNumberOfTactics = existingTactics >= 1;

				if (correctNumberOfTactics)
					super.state(context, correctNumberOfTactics, "numberOfTactics", "acme.validation.NumberOfTactics.message");
			}
			result = !super.hasErrors(context);
		}

		return result;
	}

}
