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
import acme.entities.campaign.Campaign;
import acme.entities.inventions.Invention;
import acme.entities.projects.Project;
import acme.entities.strategies.Strategy;
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
		Double averageNumberOfProjectsExcludingThemselves = this.repository.averageNumberOfProjectsByManagerExcludingThemselves(managerId);
		double desviationNumberOfProjectsByManager = numberOfMyProjects - averageNumberOfProjectsExcludingThemselves;

		Collection<Project> projects = this.repository.findProjectsByManager(managerId);

		Double minEffort = 0.0;
		Double maxEffort = 0.0;
		Double averageOfEffortOfProjectsByManager = 0.0;
		Double desviationOfTheEffortOfProjectsByManager = 0.0;

		if (!projects.isEmpty()) {
			minEffort = Double.MAX_VALUE;
			double acum = 0.0;

			// LISTA SEGURA para almacenar los cálculos y no depender del @Transient de la entidad
			java.util.List<Double> projectEfforts = new java.util.ArrayList<>();

			// 1. Recorrer los proyectos de este mánager
			for (Project p : projects) {
				double totalActiveMonths = 0.0;

				// Recuperar y sumar el de las Estrategias
				Collection<Strategy> strategies = this.repository.findStrategiesByProjectId(p.getId());
				for (Strategy s : strategies)
					if (s.getMonthsActive() != null)
						totalActiveMonths += s.getMonthsActive();

				// Recuperar y sumar el de las Campañas
				Collection<Campaign> campaigns = this.repository.findCampaignsByProjectId(p.getId());
				for (Campaign c : campaigns)
					if (c.getMonthsActive() != null)
						totalActiveMonths += c.getMonthsActive();

				// Recuperar y sumar el de las Invenciones
				Collection<Invention> inventions = this.repository.findInventionsByProjectId(p.getId());
				for (Invention i : inventions)
					if (i.getMonthsActive() != null)
						totalActiveMonths += i.getMonthsActive();

				Integer membersCount = this.repository.countMembersByProject(p.getId());

				// Calcular el esfuerzo final para este proyecto
				Double effort = 0.0;
				if (membersCount != null && membersCount > 0)
					effort = totalActiveMonths / membersCount;

				// Guardar en nuestra lista segura
				projectEfforts.add(effort);

				// Actualizar Mínimo y Máximo general
				if (effort < minEffort)
					minEffort = effort;
				if (effort > maxEffort)
					maxEffort = effort;

				acum += effort;
			}

			// 2. Calcular la media de esfuerzo
			averageOfEffortOfProjectsByManager = acum / projects.size();

			// 3. Calcular la desviación de esfuerzo iterando sobre nuestra lista segura
			double acum2 = 0.0;
			for (Double projEffort : projectEfforts)
				acum2 += Math.pow(projEffort - averageOfEffortOfProjectsByManager, 2.0);
			desviationOfTheEffortOfProjectsByManager = Math.sqrt(acum2 / projects.size());
		}

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
