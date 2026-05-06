/*
 * AdministratorAdvertisementRepository.java
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

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.advertisement.Advertisement;

@Repository
public interface AdministratorAdvertisementRepository extends AbstractRepository {

	@Query("select a from Advertisement a WHERE a.id = :id")
	Advertisement findAdvertisementById(int id);

	@Query("select a from Advertisement a")
	Collection<Advertisement> findAllAdvertisements();

}
