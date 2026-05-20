
package acme.features.inventor.invention;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.inventions.Invention;
import acme.entities.inventions.Part;
import acme.entities.projects.Project;

@Repository
public interface InventorInventionRepository extends AbstractRepository {

	@Query("select i from Invention i where i.id = :id")
	Invention findInventionById(int id);

	@Query("select i from Invention i where i.inventor.id = :inventorId")
	Collection<Invention> findInventionsByInventorId(int inventorId);

	@Query("select prt from Part prt where prt.invention.id = :inventionId")
	Collection<Part> findPartsByInventionId(int inventionId);

	@Query("SELECT COUNT(s) FROM Part s WHERE s.invention.id = :id")
	Integer getNumberOfPartsByInventionId(int id);

	@Query("SELECT pm.project  FROM ProjectMember pm  WHERE pm.member.userAccount = (SELECT i.userAccount FROM Inventor i WHERE i.id = :inventorId)")
	Collection<Project> findProjectsByInventorId(int inventorId);

}
