
package acme.entities.strategies;

import javax.persistence.Column;
import javax.persistence.Entity;

import acme.client.components.basis.AbstractRole;
import acme.client.components.validation.Mandatory;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FundRaiser extends AbstractRole {

	// Serialisation version
	private static final long	serialVersionUID	= 1L;

	// Attributes 
	@Mandatory
	@Column
	private String				bank;
	@Mandatory
	@Column
	private String				statement;
	@Mandatory
	@Column
	private Boolean				agent;

}
