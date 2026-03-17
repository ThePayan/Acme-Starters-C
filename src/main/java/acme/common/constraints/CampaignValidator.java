
package acme.common.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.campaign.Campaign;
import acme.features.any.campaign.AnyCampaignRepository;

@Validator
public class CampaignValidator extends AbstractValidator<ValidCampaign, Campaign> {

	@Autowired
	private AnyCampaignRepository campaignRepository;


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

			Boolean draftMode = campaign.getDraftMode();

			{
				boolean uniqueCampaign;
				Campaign existingCampaign = null;

				if (campaign.getTicker() != null)
					existingCampaign = this.campaignRepository.findCampaignByTicker(campaign.getTicker());

				uniqueCampaign = existingCampaign == null || existingCampaign.getId() == campaign.getId();

				super.state(context, uniqueCampaign, "ticker", "acme.validation.duplicated-ticker.message");
			}

			{
				boolean correctNumberOfMilestones;
				Integer existingMilestones = this.campaignRepository.getNumOfMilestones(campaign.getId());

				if (existingMilestones == null)
					existingMilestones = 0;

				correctNumberOfMilestones = existingMilestones >= 1;

				if (!correctNumberOfMilestones && Boolean.FALSE.equals(draftMode))
					super.state(context, false, "*", "acme.validation.NumberOfMilestones.message");
			}

			{
				Date startMoment = campaign.getStartMoment();
				Date endMoment = campaign.getEndMoment();
				boolean isAfter = false;

				if (startMoment != null && endMoment != null)
					isAfter = MomentHelper.isAfter(startMoment, endMoment);

				if (isAfter && Boolean.FALSE.equals(draftMode))
					super.state(context, false, "isAfter", "acme.validation.correctDates.message");
			}

			result = !super.hasErrors(context);
		}

		return result;
	}
}
