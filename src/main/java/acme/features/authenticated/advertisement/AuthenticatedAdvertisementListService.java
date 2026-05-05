/*
 * AuthenticatedAdvertisementListService.java
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

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.advertisement.Advertisement;

@Service
public class AuthenticatedAdvertisementListService extends AbstractService<Authenticated, Advertisement> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedAdvertisementRepository	repository;

	private Collection<Advertisement>				advertisements;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {

		this.advertisements = this.repository.findAllAdvertisements();
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.advertisements, "slogan", "picture", "target");
	}

}
