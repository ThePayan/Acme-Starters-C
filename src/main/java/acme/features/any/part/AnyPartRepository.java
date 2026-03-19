
package acme.features.any.part;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.inventions.Invention;
import acme.entities.inventions.Part;

@Repository
public interface AnyPartRepository extends AbstractRepository {

	@Query("SELECT p FROM Part p JOIN p.invention i WHERE i.id = :id")
	Collection<Part> findPartByInventionId(int id);

	@Query("SELECT prt FROM Part prt WHERE prt.id = :id")
	Part findById(int id);

	@Query("SELECT i FROM Part prt JOIN prt.invention i WHERE i.id = :id")
	Invention findInventionById(int id);

}
