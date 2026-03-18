
package acme.features.any.fundraiser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.components.principals.UserAccount;
import acme.client.services.AbstractService;
import acme.realms.Fundraiser;

@Service
public class AnyFundraiserShowService extends AbstractService<Any, Fundraiser> {

	@Autowired
	private AnyFundraiserRepository	repository;
	private Fundraiser				fundraiser;


	// AbstractService interface -------------------------------------------
	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("fundraiserId", int.class);
		this.fundraiser = this.repository.findFundraiserById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.fundraiser != null;
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		UserAccount userAccount;
		Tuple tuple;

		userAccount = this.repository.findUserAccountByFundraiserId(this.fundraiser.getId());
		tuple = super.unbindObject(this.fundraiser, "bank", "statement", "agent");
		tuple.put("userName", userAccount.getUsername());

	}

}
