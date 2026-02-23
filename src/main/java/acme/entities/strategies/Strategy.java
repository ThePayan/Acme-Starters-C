
package acme.entities.strategies;

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
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidUrl;
import acme.common.constraints.ValidHeader;
import acme.common.constraints.ValidStrategy;
import acme.common.constraints.ValidText;
import acme.common.constraints.ValidTicker;
import acme.features.fundRaiser.StrategyRepository;
import acme.realms.FundRaiser;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidStrategy
public class Strategy extends AbstractEntity {

	// Serialisation version
	private static final long	serialVersionUID	= 1L;

	// Attributes
	@Mandatory
	@Column(unique = true)
	@ValidTicker
	private String				ticker;

	@Mandatory
	@Column
	@ValidHeader
	private String				name;

	@Mandatory
	@Column
	@ValidText
	private String				description;

	@Mandatory
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startMoment;

	@Mandatory
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endMoment;

	@Optional
	@ValidUrl
	@Column
	private String				moreInfo;

	@Mandatory
	@Column
	@Valid
	private Boolean				draftMode;

	// Derived attributes

	@Transient
	@Autowired
	private StrategyRepository	repository;


	@Transient
	@Valid
	public Double getMonthsActive() {
		if (this.startMoment == null || this.endMoment == null)
			return 0.0;
		LocalDate start = this.startMoment.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate end = this.endMoment.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		long totalMonths = ChronoUnit.MONTHS.between(start, end);
		return (double) totalMonths;
	}

	@Transient
	public Double getExpectedPercentage() {
		return this.repository.getSumPercentages(this.getId());
	}


	// Relationships
	@Mandatory
	@ManyToOne
	@Valid
	private FundRaiser fundRaiser;
}
