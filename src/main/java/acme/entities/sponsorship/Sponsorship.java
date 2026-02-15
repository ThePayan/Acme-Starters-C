
package acme.entities.sponsorship;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Moment;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidUrl;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Sponsorship")
public class Sponsorship extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------
	private static final long	serialVersionUID	= 1L;

	@Mandatory
	//@ValidTicker TODO: Implement custom validation
	@Column(unique = true)
	private String				ticker;

	@Mandatory
	//@ValidHeader TODO: Implement custom validation
	@Column
	private String				name;

	@Mandatory
	//@ValidText TODO: Implement custom validation
	@Column
	private String				description;

	@Mandatory
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
	//@Temporal(TemporalType.TIMESTAMP) TODO: Fails with type Moment
	private Moment				startMoment;

	@Mandatory
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
	//@Temporal(TemporalType.TIMESTAMP) TODO: Fails with type Moment
	private Moment				endMoment;

	@Optional
	@ValidUrl
	@Column
	private String				moreInfo;


	@Transient //TODO: @Mandatory @Valid
	public Double getMonthsActive() {
		if (this.startMoment == null || this.endMoment == null)
			return 0.0;

		int startYear = this.startMoment.getYear();
		int startMonth = this.startMoment.getMonth();

		int endYear = this.endMoment.getYear();
		int endMonth = this.endMoment.getMonth();

		int totalMonths = (endYear - startYear) * 12 + endMonth - startMonth;

		return (double) totalMonths;
	}

	/*
	 * @Transient //TODO: @Mandatory @ValidMoney(positive); Implement positive attribute
	 * public Integer getTotalMoney() {
	 * }
	 */


	@Mandatory
	@Valid
	@Column
	private Boolean	draftMode;

	@Mandatory
	@Valid
	@ManyToOne
	private Sponsor	sponsor;
}
