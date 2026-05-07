/*
 * AuthenticatedAdvertisementShowService.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.advertisement.Advertisement;

@Service
public class AuthenticatedAdvertisementShowService extends AbstractService<Authenticated, Advertisement> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedAdvertisementRepository	repository;

	private Advertisement							advertisement;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.advertisement = this.repository.findAdvertisementById(id);
	}

	@Override
	public void authorise() {

		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.advertisement, "slogan", "picture", "target");
	}

}
