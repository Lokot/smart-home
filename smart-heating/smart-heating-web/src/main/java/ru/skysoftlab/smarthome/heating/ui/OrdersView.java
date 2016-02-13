package ru.skysoftlab.smarthome.heating.ui;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//import com.vaadin.addon.jpacontainer.JPAContainer;
//import com.vaadin.addon.jpacontainer.JPAContainerFactory;
//import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Table;

public class OrdersView {//extends BaseMenuView {

//	private static final long serialVersionUID = 3793954266964404200L;
//
//	@PersistenceContext(unitName = "balcon")
//	protected EntityManager em;
//
//	@EJB
//	private MyEntityProviderBean entityProvider;
//	
//	private JPAContainer<Person> persons;
//	private Table personTable;
//
//	@PostConstruct
//	private void init() {
//		// Create a persistent person container
//		persons = JPAContainerFactory.make(Person.class, em);
//		persons.setEntityProvider(entityProvider);
//		// Bind it to a component
//		personTable = new Table("Список заказ-нарядов", persons);
//		personTable.setVisibleColumns("id", "firstName", "lastName");
//		layout.addComponent(personTable);
//	}
//
//	@Override
//	public void enter(ViewChangeEvent event) {
//		
//		
//		System.out.println(event.getParameters() + " !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//		
//		// Set up sorting if the natural order is not appropriate
//		persons.sort(new String[] { "firstName", "lastName" }, new boolean[] {
//				false, false });
//	}

}
