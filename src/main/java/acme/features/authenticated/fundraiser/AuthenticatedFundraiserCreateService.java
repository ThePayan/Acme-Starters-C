
package acme.features.authenticated.fundraiser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.components.principals.UserAccount;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.realms.Fundraiser;
import acme.realms.Member;

@Service
public class AuthenticatedFundraiserCreateService extends AbstractService<Authenticated, Fundraiser> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedFundraiserRepository	repository;

	private Member								member;
	
	private Fundraiser							fundraiser;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int userAccountId;
		UserAccount userAccount;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		userAccount = this.repository.findOneUserAccountById(userAccountId);

		this.fundraiser = super.newObject(Fundraiser.class);
		this.fundraiser.setUserAccount(userAccount);
		if (!super.getRequest().getPrincipal().hasRealmOfType(Member.class)) {
			this.member = super.newObject(Member.class);
			this.member.setUserAccount(userAccount);
		}
	}

	@Override
	public void authorise() {
		boolean status;

		status = !super.getRequest().getPrincipal().hasRealmOfType(Fundraiser.class);

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.fundraiser, "bank", "statement", "agent");
	}

	@Override
	public void validate() {
		super.validateObject(this.fundraiser);
	}

	@Override
	public void execute() {
		this.repository.save(this.fundraiser);
		if (!super.getRequest().getPrincipal().hasRealmOfType(Member.class))
			this.repository.save(this.member);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.fundraiser, "bank", "statement", "agent");
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}
}
