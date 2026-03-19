
package acme.features.any.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.sponsorship.Donation;
import acme.entities.sponsorship.DonationKind;

@Service
public class AnyDonationShowService extends AbstractService<Any, Donation> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyDonationRepository	repository;

	private Donation				donation;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.donation = this.repository.findById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.donation != null && !this.donation.getSponsorship().getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(DonationKind.class, this.donation.getKind());
		tuple = super.unbindObject(this.donation, "name", "notes", "money");
		tuple.put("kind", choices);
	}
}
