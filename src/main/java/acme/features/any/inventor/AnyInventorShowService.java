
package acme.features.any.inventor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.components.principals.UserAccount;
import acme.client.services.AbstractService;
import acme.realms.Inventor;

@Service
public class AnyInventorShowService extends AbstractService<Any, Inventor> {

	@Autowired
	private AnyInventorRepository	repository;

	private Inventor				inventor;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("inventionId", int.class);
		this.inventor = this.repository.findInventorByInventionId(id);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		UserAccount userAccount;
		Tuple tuple;

		userAccount = this.repository.findUserAccountByInventorId(this.inventor.getId());
		tuple = super.unbindObject(this.inventor, "bio", "keyWords", "licensed");
		tuple.put("userName", userAccount.getUsername());

	}

}
