
package acme.realms;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Transient;

import acme.client.components.basis.AbstractRole;
import acme.client.components.basis.AbstractSquad;
import acme.client.components.validation.Mandatory;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member extends AbstractSquad {

	// Serialisation identifier -----------------------------------------------

	private static final long serialVersionUID = 1L;

	// AbstractSquad interface ------------------------------------------------


	@Mandatory
	@Transient
	@Override
	public Set<Class<? extends AbstractRole>> getMembers() {
		Set<Class<? extends AbstractRole>> result;

		result = Set.of(Spokesperson.class, Inventor.class, Fundraiser.class);

		return result;
	}
}
