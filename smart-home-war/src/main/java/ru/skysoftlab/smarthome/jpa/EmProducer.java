package ru.skysoftlab.smarthome.jpa;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EmProducer implements Serializable {

	private static final long serialVersionUID = 5127966525383571663L;

	@PersistenceContext(unitName = "smarthome-pu")
	protected EntityManager em;

	@Produces
	public EntityManager getEntityManager() {
		return em;
	}

}
