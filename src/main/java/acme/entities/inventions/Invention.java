
package acme.entities.inventions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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
@Table(name = "Invention")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Invention extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	//TODO: ValidTicker

	@Mandatory
	@Column(unique = true)
	private String				ticker;

	//TODO: ValidHeader
	@Mandatory
	@Column
	private String				name;

	//TODO: ValidText
	@Mandatory
	@Column
	private String				description;

	@Mandatory
	@ValidMoment
	//TODO: @Temporal(TemporalType.TIMESTAMP
	@Column
	private Moment				startMoment;

	@Mandatory
	@ValidMoment
	//TODO: @Temporal(TemporalType.TIMESTAMP
	@Column
	private Moment				endMoment;

	@Optional
	@ValidUrl
	@Column
	private String				moreInfo;


	@Transient //TODO: MANDATORY Y VALID
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
	 * @Transient //TODO: @Mandatory y @ValidMoney(positive)
	 * public Money getCosts(){
	 * }
	 * 
	 */


	@Mandatory
	@Valid
	@Column
	private Boolean		draftMode;

	@Mandatory
	@Valid
	@ManyToOne
	private Inventor	inventor;
}
