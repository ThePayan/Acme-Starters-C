
package acme.entities.inventions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Part extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Mandatory
	//TODO: ValidHeader
	@Column
	private String				name;

	@Mandatory
	//TODO: ValidText
	@Column
	private String				description;

	@Mandatory
	//TODO: @ValidMoney(positive)
	@Column
	private Money				cost;

	@Mandatory
	@Valid
	@Column
	private Partkind			kind;

	@Mandatory
	@Valid
	@ManyToOne
	private Invention			invention;

}
