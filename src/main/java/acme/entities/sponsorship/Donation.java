
package acme.entities.sponsorship;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidMoney;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Donation extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------
	private static final long	serialVersionUID	= 1L;

	@Mandatory
	//@ValidHeader TODO: Implement custom validation
	@Column
	private String				name;

	@Mandatory
	//@ValidText TODO: Implement custom validation
	@Column
	private String				notes;

	@Mandatory
	@ValidMoney(min = 0.0)
	@Column
	private Money				money;

	@Mandatory
	@Valid
	@Column
	private DonationKind		kind;

	@Mandatory
	@Valid
	@ManyToOne
	private Sponsorship			sponsorship;
}
