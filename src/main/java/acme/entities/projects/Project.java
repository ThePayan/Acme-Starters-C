
package acme.entities.projects;

import java.util.Date;
import java.util.List;

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
import acme.client.components.validation.ValidMoment;
import acme.common.constraints.ValidHeader;
import acme.common.constraints.ValidProject;
import acme.common.constraints.ValidText;
import acme.entities.campaign.Campaign;
import acme.entities.inventions.Invention;
import acme.entities.strategies.Strategy;
import acme.features.any.project.AnyProjectRepository;
import acme.realms.Manager;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidProject
public class Project extends AbstractEntity {

	// Serialisation version
	private static final long		serialVersionUID	= 1L;

	// Attributes
	@Mandatory
	@Column
	@ValidHeader
	private String					title;

	@Mandatory
	@Column
	@ValidText
	private String					description;

	@Mandatory
	@Column
	@ValidText
	private String					keyWords;

	@Mandatory
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date					kickOff;

	@Mandatory
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date					closeOut;

	@Mandatory
	@Column
	@Valid
	private Boolean					draftMode;

	// Derived attributes

	@Transient
	@Autowired
	private AnyProjectRepository	repository;


	@Transient
	public Double getPersonMonths() {

		if (this.repository == null || this.getId() == 0)
			return 0.0;

		double totalActiveMonths = 0.0;

		List<Strategy> strategies = this.repository.findStrategiesByProjectId(this.getId());
		for (Strategy s : strategies)
			totalActiveMonths += s.getMonthsActive();

		List<Campaign> campaigns = this.repository.findCampaignsByProjectId(this.getId());
		for (Campaign c : campaigns)
			totalActiveMonths += c.getMonthsActive();

		List<Invention> inventions = this.repository.findInventionsByProjectId(this.getId());
		for (Invention i : inventions)
			totalActiveMonths += i.getMonthsActive();

		Integer membersCount = this.repository.getNumberOfMembersByProjectId(this.getId());

		if (membersCount == null || membersCount == 0)
			return 0.0;

		return totalActiveMonths / membersCount;
	}


	// Relationships
	@Mandatory
	@ManyToOne
	@Valid
	private Manager manager;
}
