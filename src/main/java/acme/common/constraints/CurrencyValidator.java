
package acme.common.constraints;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.datatypes.Money;
import acme.client.components.validation.AbstractValidator;

public class CurrencyValidator extends AbstractValidator<ValidCurrency, Money> {

	@Override
	public boolean isValid(final Money money, final ConstraintValidatorContext context) {
		if (money == null)
			return true;

		return money.getCurrency().equals("EUR");
	}
}
