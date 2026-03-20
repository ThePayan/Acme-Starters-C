
package acme.features.inventor.part;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.inventions.Invention;
import acme.entities.inventions.Part;

@Repository
public interface InventorPartRepository extends AbstractRepository {

	@Query("select i from Invention i where i.id = :id")
	Invention findInventionById(int id);

	@Query("select prt from Part prt where prt.id = :id")
	Part findPartById(int id);

	@Query("select prt from Part prt where prt.invention.id = :inventionId")
	Collection<Part> findPartsByInventionId(int inventionId);

}
