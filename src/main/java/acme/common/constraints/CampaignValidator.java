
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

	@Autowired
	private CampaignRepository campaignRepository;


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

			// 1. Validar el número de hitos
			{
				boolean correctNumberOfMilestones;
				Integer existingMilestones = this.campaignRepository.getNumOfMilestones(campaign.getId());

				// Por si la query de base de datos devuelve nulo
				if (existingMilestones == null)
					existingMilestones = 0;

				correctNumberOfMilestones = existingMilestones >= 1;

				if (!correctNumberOfMilestones && Boolean.FALSE.equals(draftMode))
					super.state(context, false, "*", "acme.validation.NumberOfMilestones.message");
			}

			// 2. Validar que el Ticker sea único
			{
				boolean uniqueCampaign;
				Campaign existingCampaign = null;

				if (campaign.getTicker() != null)
					existingCampaign = this.campaignRepository.findCampaignByTicker(campaign.getTicker());

				uniqueCampaign = existingCampaign == null || existingCampaign.getId() == campaign.getId();

				super.state(context, uniqueCampaign, "ticker", "acme.validation.ticker.message");
			}

			// 3. Validar las fechas
			{
				Date startMoment = campaign.getStartMoment();
				Date endMoment = campaign.getEndMoment();

				if (startMoment != null && endMoment != null) {

					boolean isAfter = startMoment.after(endMoment);

					if (isAfter && Boolean.FALSE.equals(draftMode))
						super.state(context, false, "*", "acme.validation.correctDates.message");
				}
			}

			result = !super.hasErrors(context);
		}

		return result;
	}
}
