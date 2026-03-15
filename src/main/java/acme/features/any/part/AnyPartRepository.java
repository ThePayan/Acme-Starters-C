
package acme.features.any.part;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.inventions.Invention;
import acme.entities.inventions.Part;

@Repository
public interface AnyPartRepository {

	@Query("SELECT prt FROM Part prt JOIN prt.invention i WHERE i.id = :id")
	Collection<Part> findPartByReportId(int id);

	@Query("SELECT prt FROM Part prt WHERE prt.id = :id")
	Part findById(int id);

	@Query("SELECT in FROM Invention prt JOIN prt.invention in WHERE in.id = :id")
	Invention findInventionById(int id);

}
