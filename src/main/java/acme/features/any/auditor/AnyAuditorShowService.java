
package acme.features.any.auditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.components.principals.UserAccount;
import acme.client.services.AbstractService;
import acme.realms.Auditor;

@Service
public class AnyAuditorShowService extends AbstractService<Any, Auditor> {

	@Autowired
	private AnyAuditorRepository	repository;

	private Auditor					auditor;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("audit-reportId", int.class);
		this.auditor = this.repository.findAuditorByAuditReportId(id);
	}

	@Override
	public void authorise() {

		super.setAuthorised(this.auditor != null);
	}

	@Override
	public void unbind() {
		UserAccount userAccount;
		Tuple tuple;

		userAccount = this.repository.findUserAccountByAuditorId(this.auditor.getId());
		tuple = super.unbindObject(this.auditor, "firm", "highlights", "solicitor");
		tuple.put("userName", userAccount.getUsername());

	}
}
