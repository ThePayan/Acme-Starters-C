/*
 * ManagerDashboardShowService.java
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.forms.Dashboard;
import acme.realms.Manager;

@Service
public class ManagerDashboardShowService extends AbstractService<Manager, Dashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerDashboardRepository	repository;

	private Dashboard					dashboard;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		Integer userAccountId = super.getRequest().getPrincipal().getAccountId();
		Manager manager = this.repository.findManagerByUserId(userAccountId);
		Integer managerId = manager.getId();

		Integer numberOfMyProjects = this.repository.numberOfProjectsByManager(managerId);
		/////////////////////////////////////////////////////////////////////////
		Double averageNumberOfProjectsExcludingThemselves = this.repository.averageNumberOfProjectsByManagerExcludingThemselves(managerId);
		double desviationNumberOfProjectsByManager = numberOfMyProjects - averageNumberOfProjectsExcludingThemselves;

		Collection<Project> projects = this.repository.findProjectsByManager(managerId);
		////////////////////////////////////////////////////////////////////////
		Double minEffort = 0.;
		for (Project p : projects)
			if (minEffort > p.getPersonMonths())
				minEffort = p.getPersonMonths();
		////////////////////////////////////////////////////////////////////////
		Double maxEffort = 0.;
		for (Project p : projects)
			if (maxEffort < p.getPersonMonths())
				maxEffort = p.getPersonMonths();
		////////////////////////////////////////////////////////////////////////
		Double averageOfEffortOfProjectsByManager;
		int contador = 0;
		double acum = 0.;
		for (Project p : projects) {
			contador += 1;
			acum += p.getPersonMonths();
		}
		averageOfEffortOfProjectsByManager = acum / contador;
		///////////////////////////////////////////////////////////////////////////////
		Double desviationOfTheEffortOfProjectsByManager;
		double acum2 = 0.;
		int contador2 = 0;
		for (Project p : projects) {
			contador2 += 1;
			acum2 += Math.pow(p.getPersonMonths() - averageOfEffortOfProjectsByManager, 2.);
		}
		desviationOfTheEffortOfProjectsByManager = Math.sqrt(acum2 / contador2);
		///////////////////////////////////////////////////////////////////////////////
		this.dashboard = super.newObject(Dashboard.class);
		this.dashboard.setNumberOfMyProjects(numberOfMyProjects);
		this.dashboard.setDesviationNumberOfProjectsByManager(desviationNumberOfProjectsByManager);
		this.dashboard.setMinEffort(minEffort);
		this.dashboard.setMaxEffort(maxEffort);
		this.dashboard.setAverageOfEffortOfProjectsByManager(averageOfEffortOfProjectsByManager);
		this.dashboard.setDesviationOfTheEffortByProjectsByManager(desviationOfTheEffortOfProjectsByManager);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.dashboard, //
			"numberOfMyProjects", "desviationNumberOfProjectsByManager", // 
			"minEffort", "maxEffort", //
			"averageOfEffortOfProjectsByManager", "desviationOfTheEffortByProjectsByManager");
	}

}
