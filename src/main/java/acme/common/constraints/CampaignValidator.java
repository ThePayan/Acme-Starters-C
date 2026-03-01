
package acme.common.constraints;

import java.util.Date;

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
			{
				boolean correctNumberOfMilestones;
				Integer existingMilestones;
				existingMilestones = this.campaignRepository.getNumOfMilestones(campaign.getId());

				correctNumberOfMilestones = existingMilestones >= 1;

				if (!correctNumberOfMilestones && campaign.getDraftMode())
					super.state(context, correctNumberOfMilestones, "*", "acme.validation.NumberOfMilestones.message");

			}
			{
				boolean uniqueCampaign;
				Campaign existingCampaign;

				existingCampaign = this.campaignRepository.findCampaignByTicker(campaign.getTicker());
				uniqueCampaign = existingCampaign == null || existingCampaign.equals(campaign);

				super.state(context, uniqueCampaign, "ticker", "acme.validation.ticker.message");
			}
			{
				boolean isAfter;

				Date startMoment = campaign.getStartMoment();
				Date endMoment = campaign.getEndMoment();
				if (startMoment != null || endMoment != null) {

					isAfter = startMoment.after(endMoment);

					if (isAfter && campaign.getDraftMode())
						super.state(context, isAfter, "*", "acme.validation.correctDates.message");
				}

			}

			result = !super.hasErrors(context);
		}

		return result;
	}

}
