
package acme.entities.sponsorship;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.Valid;

import acme.client.components.basis.AbstractRole;
import acme.client.components.validation.Mandatory;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sponsor extends AbstractRole {

	// Serialisation identifier -----------------------------------------------
	private static final long	serialVersionUID	= 1L;

	@Mandatory
	//@ValidText TODO: Implement custom validation
	@Column
	private String				address;

	@Mandatory
	//@ValidHeader TODO: Implement custom validation
	@Column
	private String				im;

	@Mandatory
	@Valid
	@Column
	private Boolean				gold;
}
