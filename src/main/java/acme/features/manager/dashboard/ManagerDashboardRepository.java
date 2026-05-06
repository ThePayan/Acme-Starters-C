/*
 * ManagerDashboardRepository.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.manager.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.realms.Manager;

@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	@Query("select count(p) from Project p where p.manager.id = :id")
	Integer numberOfProjectsByManager(int id);

	@Query(value = "SELECT IFNULL(AVG(project_count), 0) FROM (" + "  SELECT COUNT(id) as project_count " + "  FROM project " + "  WHERE manager_id != :id " + "  GROUP BY manager_id" + ") as subquery", nativeQuery = true)
	Double averageNumberOfProjectsByManagerExcludingThemselves(int id);

	@Query("SELECT p FROM Project p WHERE p.manager.id = :id")
	Collection<Project> findProjectsByManager(int id);

	@Query("SELECT m from Manager m where m.userAccount.id = :id")
	Manager findManagerByUserId(int id);

}
