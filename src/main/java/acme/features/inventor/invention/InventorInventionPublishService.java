
package acme.features.inventor.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.realms.Inventor;

@Service
public class InventorInventionPublishService extends AbstractService<Inventor, Invention> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private InventorInventionRepository	repository;

	private Invention					invention;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.invention = this.repository.findInventionById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.invention != null && this.invention.getDraftMode() && this.invention.getInventor().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.invention);

		{
			boolean correctNumberOfParts;
			correctNumberOfParts = this.repository.getNumberOfPartsByInventionId(this.invention.getId()) >= 1;
			super.state(correctNumberOfParts, "*", "acme.validation.numberOfParts.message");
		}
		{
			boolean isBefore;
			isBefore = this.invention.getStartMoment().before(this.invention.getEndMoment());
			super.state(isBefore, "startMoment", "acme.validation.correctDates.message");
			super.state(isBefore, "endMoment", "acme.validation.correctDates.message");
		}
	}

	@Override
	public void execute() {
		this.invention.setDraftMode(false);
		this.repository.save(this.invention);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}

}
