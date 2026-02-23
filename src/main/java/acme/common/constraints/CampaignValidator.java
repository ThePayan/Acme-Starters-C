
package acme.common.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.campaign.Campaign;
import acme.features.authenticated.spokesperson.CampaignRepository;

@Validator
public class CampaignValidator extends AbstractValidator<ValidCampaign, Campaign> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private CampaignRepository campaignRepository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidCampaign annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Campaign campaign, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (campaign == null)
			result = true;
		else {
			boolean correctNumberOfMilestones;
			Integer existingMilestones;
			existingMilestones = this.campaignRepository.getNumOfMilestones(campaign.getId());

			correctNumberOfMilestones = existingMilestones >= 1;

			if (correctNumberOfMilestones)
				super.state(context, correctNumberOfMilestones, "numberOfMilestones", "acme.validation.NumberOfTactics.message");
		}
		result = !super.hasErrors(context);
		return result;
	}

}
