
package acme.entities.inventions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.Valid;

import acme.client.components.basis.AbstractRole;
import acme.client.components.validation.Mandatory;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Inventor")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Inventor extends AbstractRole {

	private static final long	serialVersionUID	= 1L;

	@Mandatory
	//TODO: ValidText
	@Column
	private String				bio;

	@Mandatory
	//TODO: ValidHeader
	@Column
	private String				keyWords;

	@Mandatory
	@Valid
	@Column
	private Boolean				licensed;

}
