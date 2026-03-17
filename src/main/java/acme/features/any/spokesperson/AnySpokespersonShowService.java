/*
 * AnySpokespersonShowService.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.any.spokesperson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.realms.Spokesperson;

@Service
public class AnySpokespersonShowService extends AbstractService<Any, Spokesperson> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnySpokespersonRepository	repository;

	private Spokesperson				spokesperson;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("campaignId", int.class);
		this.spokesperson = this.repository.findSpokespersonById(id);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.spokesperson, //
			"cv", "achievements", "licensed");
	}

}
