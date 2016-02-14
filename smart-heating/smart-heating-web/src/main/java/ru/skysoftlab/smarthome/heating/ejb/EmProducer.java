package ru.skysoftlab.smarthome.heating.ejb;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateful
public class EmProducer {

	@PersistenceContext(unitName = "smartHeating-pu")
	protected EntityManager em;

	public EntityManager getEM() {
		return em;
	}
}
