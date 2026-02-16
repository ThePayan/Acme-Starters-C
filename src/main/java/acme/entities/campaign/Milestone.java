
package acme.entities.campaign;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidNumber;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Milestone")
public class Milestone extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	//TODO: add the others validators when they'll be done

	@Mandatory
	@Column
	private String				title;

	@Mandatory
	@Column
	private String				achievements;

	@Mandatory
	@ValidNumber(min = 0)
	@Column
	private Double				effort;

	/*
	 * TODO discomment when the datatype is ready
	 * 
	 * @Mandatory
	 * 
	 * @Column
	 * private MilestoneKind kind;
	 */

	@Mandatory
	@ManyToOne
	private Campaign			campaign;

}
