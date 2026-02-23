
package acme.realms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.Valid;

import acme.client.components.basis.AbstractRole;
import acme.client.components.validation.Mandatory;
import acme.common.constraints.ValidHeader;
import acme.common.constraints.ValidText;
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
	@ValidHeader
	private String				bank;
	@Mandatory
	@Column
	@ValidText
	private String				statement;
	@Mandatory
	@Column
	@Valid
	private Boolean				agent;

}
