
package acme.entities.auditreport;

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
@Table(name = "Auditor")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Auditor extends AbstractRole {

	// Serialisation identifier -----------------------------------------------
	private static final long	serialVersionUID	= 1L;

	@Mandatory
	//@ValidHeader TODO: Do it lol
	@Column
	private String				firm;

	@Mandatory
	//@ValidHeader TODO: same lol
	@Column
	private String				highlights;

	@Mandatory
	@Valid
	@Column
	private Boolean				solicitor;

}
