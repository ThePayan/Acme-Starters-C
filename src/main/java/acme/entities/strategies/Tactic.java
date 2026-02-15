
package acme.entities.strategies;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Tactic extends AbstractEntity {

	// Serialisation version
	private static final long	serialVersionUID	= 1L;

	// Attributes
	@Mandatory
	@Column
	private String				name;
	@Mandatory
	@Column
	private String				notes;
	@Mandatory
	@Column
	private Double				expectedPercentage;
	@Mandatory
	@Column
	private TacticKind			kind;

	// Relationships
	@Mandatory
	@ManyToOne
	private Strategy			strategy;
}
