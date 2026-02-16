
package acme.entities.campaign;

import java.beans.Transient;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidUrl;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Campaign")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Campaign extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	//TODO: add the others validators when they'll be done
	@Mandatory
	@Column(unique = true)
	private String				ticker;

	@Mandatory
	@Column
	private String				name;

	@Mandatory
	@Column
	private String				description;

	@Mandatory
	@ValidMoment() // TODO put future and change to Moment
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startMoment;

	@Mandatory
	@ValidMoment() // TODO put future and change to Moment
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endMoment;

	@Optional
	@ValidUrl
	@Column
	private String				moreInfo;

	@Mandatory
	@Valid
	@ManyToOne
	private Spokesperson		spokesperson;


	@Transient
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
	 * TODO Cuando tenga el repository podré hacerlo
	 * 
	 * @Transient
	 * public Double getEffort() {
	 * 
	 * }
	 */


	@Mandatory
	@Column
	private Boolean draftMode;

}
