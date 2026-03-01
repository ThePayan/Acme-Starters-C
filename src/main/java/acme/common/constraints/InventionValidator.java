
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

		// 1. Si es null
		if (invention == null)
			return true;

		// 2. Validación: Número de secciones
		Integer existingInvention = this.inventionRepository.getNumberOfInventions(invention.getId());
		boolean correctNumberOfInventionSections = existingInvention != null && existingInvention >= 1;

		if (!correctNumberOfInventionSections && !invention.getDraftMode())
			super.state(context, false, "*", "acme.validation.NumberOfInventionSections.message");

		// 3. Validación: Unicidad del Ticker
		Invention existingInventionTicker = this.inventionRepository.findInventionByTicker(invention.getTicker());
		boolean isUnique = existingInventionTicker == null || existingInventionTicker.getId() == invention.getId();
		super.state(context, isUnique, "ticker", "acme.validation.ticker.message");

		// 4. Validación: Fechas
		Date start = invention.getStartMoment();
		Date end = invention.getEndMoment();

		if (start != null && end != null && invention.getDraftMode()) {
			boolean isBefore = start.before(end);
			super.state(context, isBefore, "*", "acme.validation.correctDates.message");
		}

		return !super.hasErrors(context);
	}
}
