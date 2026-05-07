
package acme.features.authenticated.inventor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.components.principals.UserAccount;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.realms.Inventor;
import acme.realms.Member;

@Service
public class AuthenticatedInventorCreateService extends AbstractService<Authenticated, Inventor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedInventorRepository	repository;

	private Inventor						inventor;

	private Member							member;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int userAccountId;
		UserAccount userAccount;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		userAccount = this.repository.findOneUserAccountById(userAccountId);

		this.inventor = super.newObject(Inventor.class);
		this.inventor.setUserAccount(userAccount);
		if (!super.getRequest().getPrincipal().hasRealmOfType(Member.class)) {
			this.member = super.newObject(Member.class);
			this.member.setUserAccount(userAccount);
		}
	}

	@Override
	public void authorise() {
		boolean status;

		status = !super.getRequest().getPrincipal().hasRealmOfType(Inventor.class);

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.inventor, "bio", "keyWords", "licensed");
	}

	@Override
	public void validate() {
		super.validateObject(this.inventor);
	}

	@Override
	public void execute() {
		this.repository.save(this.inventor);
		if (!super.getRequest().getPrincipal().hasRealmOfType(Member.class))
			this.repository.save(this.member);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.inventor, "bio", "keyWords", "licensed");
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}
