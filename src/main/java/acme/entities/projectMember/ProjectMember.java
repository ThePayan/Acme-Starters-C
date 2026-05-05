
package acme.entities.projectMember;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.common.constraints.ValidProjectMember;
import acme.entities.project.Project;
import acme.realms.Member;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidProjectMember
public class ProjectMember extends AbstractEntity {

	//Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@Valid
	@Column
	private Role				role;

	// Relationships ----------------------------------------------------------

	@Mandatory
	@Valid
	@ManyToOne
	private Member				member;

	@Mandatory
	@Valid
	@ManyToOne
	private Project				project;

}
