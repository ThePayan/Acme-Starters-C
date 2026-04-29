
package acme.features.any.project;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AnyProjectRepository extends AbstractRepository {

	@Query("select count(i) from Invention i where i.project.id = :projectId")
	Integer getNumOfInventions(int projectId);

	@Query("select count(c) from Campaign c where c.project.id = :projectId")
	Integer getNumOfCampaigns(int projectId);

	@Query("select count(s) from Strategy s where s.project.id = :projectId")
	Integer getNumOfStrategies(int projectId);

}
