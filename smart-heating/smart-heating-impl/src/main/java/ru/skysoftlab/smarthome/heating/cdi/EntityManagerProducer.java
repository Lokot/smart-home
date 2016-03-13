package ru.skysoftlab.smarthome.heating.cdi;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

public class EntityManagerProducer implements Serializable {
	
	private static final long serialVersionUID = -4967800174115853586L;
	
	@PersistenceUnit(unitName = "smartHeating-pu")
	private EntityManagerFactory emf;

	@Produces
	@RequestScoped
	public EntityManager create() {
		return emf.createEntityManager();
	}

	public void close(@Disposes EntityManager em) {
		if (em.isOpen()) {
			em.close();
		}
	}
}
