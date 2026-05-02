
package acme.features.any.project;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;

@Repository
public interface AnyProjectRepository extends AbstractRepository {

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findProjectById(int id);

	@Query("SELECT p FROM Project p WHERE p.draftMode = false")
	List<Project> findAllPublishedProjects();

	@Query("select count(i) from Invention i where i.project.id = :projectId")
	Integer getNumOfInventions(int projectId);

}
