
package acme.features.any.invention;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.inventions.Invention;
import acme.entities.projectMember.ProjectMember;
import acme.entities.projectMember.Role;

@Repository
public interface AnyInventionRepository extends AbstractRepository {

	@Query("select sum(p.cost.amount) from Part p where p.invention.id = :id and p.cost.currency = 'EUR'")
	Double getSumOfCosts(int id);

	@Query("SELECT COUNT(s) FROM Part s WHERE s.invention.id = :id")
	Integer getNumOfParts(@Param("id") int id);

	@Query("SELECT i FROM Invention i WHERE i.draftMode = false")
	List<Invention> findAllPublishedInventions();

	Invention findInventionByTicker(String ticker);

	@Query("SELECT i FROM Invention i WHERE i.id = :id")
	Invention findInventionById(int id);

	@Query("SELECT i FROM Invention i WHERE i.project.id = :projectId")
	List<Invention> findInventionsByProjectId(int projectId);

	@Query("SELECT m.id FROM Member m WHERE m.userAccount.id = :id")
	int findMemberIdByUserAccountId(int id);

	@Query("SELECT pm FROM ProjectMember pm WHERE pm.role = :role AND pm.member.id = :memberId AND pm.project.id = :projectId")
	ProjectMember findProjectMemberByRoleAndMemberIdAndProjectId(Role role, int memberId, int projectId);
}
