package ru.skysoftlab.smarthome.heating.cdi;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EmProducer implements Serializable {

	private static final long serialVersionUID = 5127966525383571663L;

	@PersistenceContext(unitName = "smartHeating-pu")
	protected EntityManager em;

	@Produces
	public EntityManager getEntityManager() {
		return em;
	}

}
