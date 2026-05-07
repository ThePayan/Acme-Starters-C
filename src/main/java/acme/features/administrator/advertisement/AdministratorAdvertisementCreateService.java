/*
 * AdministratorAdvertisementCreateService.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Administrator;
import acme.client.services.AbstractService;
import acme.entities.advertisement.Advertisement;

@Service
public class AdministratorAdvertisementCreateService extends AbstractService<Administrator, Advertisement> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorAdvertisementRepository	repository;

	private Advertisement							advertisement;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {

		this.advertisement = super.newObject(Advertisement.class);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void bind() {
		super.bindObject(this.advertisement, "slogan", "picture", "target");
	}

	@Override
	public void validate() {
		super.validateObject(this.advertisement);
	}

	@Override
	public void execute() {
		this.repository.save(this.advertisement);
	}

	@Override
	public void unbind() {

		super.unbindObject(this.advertisement, "slogan", "picture", "target");

	}

}
