
package acme.features.member.auditReport;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditreport.AuditReport;
import acme.entities.projectMember.ProjectMember;
import acme.entities.projects.Project;

@Repository
public interface MemberAuditReportRepository extends AbstractRepository {

	AuditReport findAuditReportByTicker(String ticker);

	@Query("SELECT ar FROM AuditReport ar WHERE ar.id = :id")
	AuditReport findAuditReportById(int id);

	@Query("SELECT a FROM AuditReport a WHERE a.project.id = :projectId")
	List<AuditReport> findAuditReportsByProjectId(int projectId);

	@Query("SELECT pm FROM ProjectMember pm WHERE pm.project.id = :projectId AND pm.member.id = :memberId")
	Collection<ProjectMember> findProjectMembersByProjectIdAndMemberId(int memberId, int projectId);

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findProjectById(int id);
}
