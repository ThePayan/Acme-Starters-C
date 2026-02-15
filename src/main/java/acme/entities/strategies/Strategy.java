
package acme.entities.strategies;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Moment;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Strategy extends AbstractEntity {

	// Serialisation version
	private static final long	serialVersionUID	= 1L;

	// Attributes
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
	//@Temporal(TemporalType.TIMESTAMP)
	private Moment				startMoment;
	@Mandatory
	//@Temporal(TemporalType.TIMESTAMP)
	private Moment				endMoment;
	@Optional
	@Column
	private String				moreInfo;

	//private Double				monthsActive; Esto es uno derivado
	//private Double				expectedPercentage; Esto es uno derivado
	@Mandatory
	@Column
	private Boolean				draftMode;

	// Derived attributes

	// Relationships
	@Mandatory
	@ManyToOne
	private FundRaiser			fundRaiser;
}
