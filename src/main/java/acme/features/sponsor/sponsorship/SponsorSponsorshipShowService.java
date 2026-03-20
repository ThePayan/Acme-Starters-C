
package acme.features.sponsor.sponsorship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.datatypes.Money;
import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.sponsorship.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorSponsorshipShowService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository	repository;

	private Sponsorship						sponsorship;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.sponsorship = this.repository.findSponsorshipById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.sponsorship != null && //
			(this.sponsorship.getSponsor().isPrincipal() || !this.sponsorship.getDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		double months = this.sponsorship.getMonthsActive();
		Money money = this.sponsorship.getTotalMoney();
		tuple = super.unbindObject(this.sponsorship, //
			"ticker", "startMoment", "endMoment", "name", //
			"description", "moreInfo", "draftMode");
		tuple.put("monthsActive", months);
		tuple.put("totalMoney", money);
	}

}
