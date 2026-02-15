
package acme.entities.auditreport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidNumber;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "AuditSection")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class AuditSection extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------
	private static final long	serialVersionUID	= 1L;

	@Mandatory
	//@ValidHeader
	@Column
	private String				name;

	@Mandatory
	//@ValidHeader
	@Column
	private String				notes;

	@Mandatory
	@ValidNumber(min = 0)
	@Column
	private Integer				hours;

	@Mandatory
	@Valid
	@Column
	private SectionKind			kind;

	@Mandatory
	@Valid
	@ManyToOne
	private AuditReport			auditReport;

}
