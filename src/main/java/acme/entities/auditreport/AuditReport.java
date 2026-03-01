
package acme.entities.auditreport;

import java.time.Duration;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MomentHelper;
import acme.common.constraints.ValidAudit;
import acme.common.constraints.ValidHeader;
import acme.common.constraints.ValidText;
import acme.common.constraints.ValidTicker;
import acme.features.authenticated.auditreport.AuthenticatedAuditReportRepository;
import acme.realms.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidAudit
public class AuditReport extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------
	private static final long					serialVersionUID	= 1L;

	@Transient
	@Autowired
	private AuthenticatedAuditReportRepository	auditReport;

	@ValidTicker
	@Mandatory
	@Column(unique = true)
	private String								ticker;

	@Mandatory
	@Column
	@ValidHeader
	private String								name;

	@ValidText
	@Mandatory
	@Column
	private String								description;

	@Mandatory
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
	@Column
	private Date								startMoment;

	@Mandatory
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
	@Column
	private Date								endMoment;

	@Optional
	@ValidUrl
	@Column
	private String								moreInfo;


	@Valid
	@Transient
	public Double getMonthsActive() {
		Duration duration = MomentHelper.computeDuration(this.startMoment, this.endMoment);
		double result = duration.toSeconds() / 2592000.0;
		return result;
	}

	@Transient
	public Integer getAllHours() {
		return this.auditReport.getAllHours(this.getId());
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
