
package acme.features.any.sponsor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.components.principals.UserAccount;
import acme.client.services.AbstractService;
import acme.realms.Sponsor;

@Service
public class AnySponsorShowService extends AbstractService<Any, Sponsor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnySponsorRepository	repository;

	private Sponsor					sponsor;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("sponsorshipId", int.class);
		this.sponsor = this.repository.findSponsorBySponsorshipId(id);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		UserAccount userAccount;
		Tuple tuple;

		userAccount = this.repository.findUserAccountBySponsorId(this.sponsor.getId());
		tuple = super.unbindObject(this.sponsor, "address", "im", "gold");
		tuple.put("userName", userAccount.getUsername());
	}
}
