
package acme.entities.sponsorship;

import java.time.Duration;
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
import acme.client.helpers.MomentHelper;
import acme.common.constraints.ValidHeader;
import acme.common.constraints.ValidText;
import acme.common.constraints.ValidTicker;
import acme.features.authenticated.sponsor.AuthenticatedSponsorRepository;
import acme.realms.Sponsor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sponsorship extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------
	private static final long				serialVersionUID	= 1L;

	@Mandatory
	@ValidTicker
	@Column(unique = true)
	private String							ticker;

	@Mandatory
	@ValidHeader
	@Column
	private String							name;

	@Mandatory
	@ValidText
	@Column
	private String							description;

	@Mandatory
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date							startMoment;

	@Mandatory
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date							endMoment;

	@Optional
	@ValidUrl
	@Column
	private String							moreInfo;

	@Transient
	@Autowired
	private AuthenticatedSponsorRepository	sponsorRep;


	@Transient
	@Valid
	public Double getMonthsActive() {
		if (this.startMoment == null || this.endMoment == null)
			return 0.0;

		Duration duration = MomentHelper.computeDuration(this.startMoment, this.endMoment);
		double result = duration.toSeconds() / 2592000.0;
		return result;
	}

	@Transient
	public Money getTotalMoney() {
		Double totalAmount = this.sponsorRep.getTotalMoney(this.getId());
		Money money = new Money();
		money.setAmount(totalAmount != null ? totalAmount : 0.0);
		money.setCurrency("EUR");
		return money;
	}


	@Mandatory
	@Valid
	@Column
	private Boolean	draftMode;

	@Mandatory
	@Valid
	@ManyToOne
	private Sponsor	sponsor;
}
