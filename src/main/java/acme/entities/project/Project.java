
package acme.entities.project;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidMoment;
import acme.common.constraints.ValidHeader;
import acme.common.constraints.ValidProject;
import acme.common.constraints.ValidText;
import acme.realms.Manager;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidProject
public class Project extends AbstractEntity {

	// Serialisation version
	private static final long	serialVersionUID	= 1L;

	// Attributes
	@Mandatory
	@Column
	@ValidHeader
	private String				title;

	@Mandatory
	@Column
	@ValidText
	private String				description;

	@Mandatory
	@Column
	@ValidText
	private String				keyWords;

	@Mandatory
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				kickOff;

	@Mandatory
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				closeOut;

	@Mandatory
	@Column
	@Valid
	private Boolean				draftMode;

	// Derived attributes
	// En proceso, se necesita Member 

	// Relationships
	@Mandatory
	@ManyToOne
	@Valid
	private Manager				manager;
}
