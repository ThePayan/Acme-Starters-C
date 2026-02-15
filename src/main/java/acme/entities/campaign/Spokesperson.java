
package acme.entities.campaign;

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
@Table(name = "Spokesperson")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Spokesperson extends AbstractRole {

	private static final long	serialVersionUID	= 1L;

	//TODO: add the others validators when they'll be done

	@Mandatory
	@Column
	private String				cv;

	@Mandatory
	@Column
	private String				achievements;

	@Mandatory
	@Valid
	@Column
	private Boolean				licensed;

}
