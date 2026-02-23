
package acme.entities.campaign;

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
import acme.common.constraints.ValidText;
import acme.common.constraints.ValidTicker;
import acme.features.authenticated.spokesperson.CampaignRepository;
import acme.realms.Spokesperson;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Campaign extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Mandatory
	@ValidTicker
	@Column(unique = true)
	private String				ticker;

	@Mandatory
	@ValidHeader
	@Column
	private String				name;

	@Mandatory
	@ValidText
	@Column
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
	@Valid
	@ManyToOne
	private Spokesperson		spokesperson;

	@Transient
	@Autowired
	private CampaignRepository	campaignRepository;


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
	public Double getEffort() {
		Double result;
		Double wrapper;

		wrapper = this.campaignRepository.getEfforts(this.getId());
		result = wrapper == null ? 0 : wrapper.doubleValue();

		return result;
	}


	@Mandatory
	@Valid
	@Column
	private Boolean draftMode;

}
