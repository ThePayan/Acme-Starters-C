
package acme.features.manager.projectmember;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaign.Campaign;
import acme.entities.inventions.Invention;
import acme.entities.projectMember.ProjectMember;
import acme.entities.projectMember.Role;
import acme.entities.projects.Project;
import acme.entities.strategies.Strategy;
import acme.realms.Fundraiser;
import acme.realms.Inventor;
import acme.realms.Member;
import acme.realms.Spokesperson;

@Repository
public interface ManagerProjectMemberRepository extends AbstractRepository {

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findProjectById(int id);

	@Query("SELECT m FROM Member m WHERE m not in (SELECT pm.member FROM ProjectMember pm WHERE pm.project.id = :id)")
	Collection<Member> findAllAvaliableMembersByProjectId(int id);

	@Query("SELECT m FROM Member m WHERE m.id = :id")
	Member findMemberById(int id);

	@Query("SELECT pm FROM ProjectMember pm WHERE pm.project.id = :id")
	Collection<ProjectMember> findProjectMembersByProjectId(int id);

	@Query("SELECT pm FROM ProjectMember pm WHERE pm.id = :id")
	ProjectMember findProjectMemberById(int id);

	@Query("SELECT pm.member FROM ProjectMember pm WHERE pm.project.id = :id")
	Collection<Member> findMembersByProjectId(int id);

	@Query("SELECT pm FROM ProjectMember pm WHERE pm.role = :role AND pm.member.id = :memberId AND pm.project.id = :projectId")
	ProjectMember findByRoleAndMemberIdAndProjectId(Role role, int memberId, int projectId);

	@Query("SELECT i FROM Inventor i where i.userAccount.id = :id")
	Inventor findInventorByUserAccountId(int id);

	@Query("SELECT i FROM Invention i where i.inventor.id = :roleId and i.project.id = :projectId")
	Collection<Invention> findInventionByInventorIdAndProjectId(int roleId, int projectId);

	@Query("SELECT f from Fundraiser f where f.userAccount.id = :id")
	Fundraiser findFundraiserByUserAccountId(int id);

	@Query("SELECT s from Strategy s where s.fundraiser.id = :roleId and s.project.id = :projectId")
	Collection<Strategy> findStrategyByFundraiserIdAndProjectId(int roleId, int projectId);

	@Query("SELECT s from Spokesperson s where s.userAccount.id = :id")
	Spokesperson findSpokespersonByUserAccountId(int id);

	@Query("SELECT c from Campaign c where c.spokesperson.id = :roleId and c.project.id = :projectId")
	Collection<Campaign> findCampaignBySpokespersonIdAndProjectId(int roleId, int projectId);

}
