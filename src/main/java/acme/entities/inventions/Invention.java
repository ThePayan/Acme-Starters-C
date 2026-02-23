
package acme.entities.inventions;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidUrl;
import acme.common.constraints.ValidHeader;
import acme.common.constraints.ValidText;
import acme.common.constraints.ValidTicker;
import acme.features.authenticated.inventions.AuthenticatedInventionRepository;
import acme.realms.Inventor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Invention extends AbstractEntity {

	private static final long					serialVersionUID	= 1L;

	@Transient
	@Autowired
	private AuthenticatedInventionRepository	inventionRep;

	@ValidTicker
	@Mandatory
	@Column(unique = true)
	private String								ticker;

	@ValidHeader
	@Mandatory
	@Column
	private String								name;

	@ValidText
	@Mandatory
	@Column
	private String								description;

	@Mandatory
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date								startMoment;

	@Mandatory
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date								endMoment;

	@Optional
	@ValidUrl
	@Column
	private String								moreInfo;


	@Valid
	@Transient
	public Double getMonthsActive() {
		if (this.startMoment == null || this.endMoment == null)
			return 0.0;

		LocalDate start = this.startMoment.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		LocalDate end = this.endMoment.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		long totalMonths = ChronoUnit.MONTHS.between(start, end);

		return (double) totalMonths;

	}

	@Transient
	public Money getCosts() {
		Double totalAmount = this.inventionRep.getSumOfCosts(this.getId());

		Money money = new Money();

		money.setAmount(totalAmount != null ? totalAmount : 0.0);

		money.setCurrency("EUR");

		return money;
	}


	@Mandatory
	@Valid
	@Column
	private Boolean		draftMode;

	@Mandatory
	@Valid
	@ManyToOne
	private Inventor	inventor;
}
