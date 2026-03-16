
package acme.entities.auditreport;

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
import acme.client.components.validation.ValidNumber;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MomentHelper;
import acme.common.constraints.ValidAudit;
import acme.common.constraints.ValidHeader;
import acme.common.constraints.ValidText;
import acme.common.constraints.ValidTicker;
import acme.features.any.auditreport.AnyAuditReportRepository;
import acme.realms.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidAudit
public class AuditReport extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------
	private static final long			serialVersionUID	= 1L;

	@Transient
	@Autowired
	private AnyAuditReportRepository	auditReport;

	@ValidTicker
	@Mandatory
	@Column(unique = true)
	private String						ticker;

	@Mandatory
	@Column
	@ValidHeader
	private String						name;

	@ValidText
	@Mandatory
	@Column
	private String						description;

	@Mandatory
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date						startMoment;

	@Mandatory
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date						endMoment;

	@Optional
	@ValidUrl
	@Column
	private String						moreInfo;


	@Mandatory
	@Valid
	@Transient
	public Double getMonthsActive() {
		if (this.startMoment == null || this.endMoment == null)
			return 0.0;
		double result = MomentHelper.computeDifference(this.startMoment, this.endMoment, ChronoUnit.MONTHS);

		return result;
	}

	@Mandatory
	@ValidNumber(min = 0)
	@Transient
	public Integer getAllHours() {
		Integer horas = this.auditReport.getAllHours(this.getId());
		if (horas == null)
			return 0;
		return horas;
	}


	@Mandatory
	@Valid
	@Column
	private Boolean	draftMode;

	@Mandatory
	@Valid
	@ManyToOne
	private Auditor	auditor;

}
