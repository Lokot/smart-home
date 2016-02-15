package ru.skysoftlab.smarthome.heating.ui;

import javax.ejb.EJB;
import javax.ejb.EJBAccessException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.hibernate.ejb.EntityManagerFactoryImpl;

import ru.skysoftlab.smarthome.heating.ejb.EmProducer;
import ru.skysoftlab.smarthome.heating.ejb.MyEntityProviderBean;
import ru.skysoftlab.smarthome.heating.ejb.TestBean;
import ru.skysoftlab.smarthome.heating.entitys.Sensor;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.provider.CachingLocalEntityProvider;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Notification.Type;

public class OrdersView extends BaseMenuView {

	private static final long serialVersionUID = 3793954266964404200L;

	public static final String NAME = "sensors1";

//	@PersistenceContext(unitName = "smartHeating-pu")
	protected EntityManager em;

	// @EJB
	// private MyEntityProviderBean entityProvider;

	private JPAContainer<Sensor> persons;
	private Table personTable;

	public OrdersView() {
		super();
		init();
	}

	private void init() {
		
		
		try {
			EmProducer pr = (EmProducer) new InitialContext().lookup("java:module/" + EmProducer.class.getSimpleName());
			em = pr.getEM();
		} catch (NamingException | EJBAccessException e) {
			Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
		}
		
		// Create a persistent person container
		persons = JPAContainerFactory.make(Sensor.class, em);
		MyEntityProviderBean entityProvider;
		try {
			entityProvider = (MyEntityProviderBean) new InitialContext().lookup("java:module/" + MyEntityProviderBean.class.getSimpleName());
			persons.setEntityProvider(entityProvider);
		} catch (NamingException | EJBAccessException e) {
			Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
		}
		
		// Bind it to a component
		personTable = new Table("Список заказ-нарядов", persons);
		personTable.setVisibleColumns("sensorId", "name", "maxTemp");
		layout.addComponent(personTable);

		
//		persons.addEntity(sen);
	}

	@Override
	public void enter(ViewChangeEvent event) {

		// Set up sorting if the natural order is not appropriate
		persons.sort(new String[] { "sensorId", "name", "maxTemp" },
				new boolean[] { false, false, false });
	}

}
