package ru.skysoftlab.smarthome.heating.ui;

import javax.ejb.EJBAccessException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;

import ru.skysoftlab.smarthome.heating.ejb.EmProducer;
import ru.skysoftlab.smarthome.heating.ejb.MyEntityProviderBean;
import ru.skysoftlab.smarthome.heating.entitys.Sensor;
import ru.skysoftlab.smarthome.heating.ui.forms.SensorsForm;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class SensorsView extends BaseMenuView {

	private static final long serialVersionUID = 6698245813955647506L;

	public static final String NAME = "sensors";

	TextField filter = new TextField();
	public Grid contactList = new Grid();
	Button newContact = new Button("New contact");
	SensorsForm contactForm = new SensorsForm(this);

	private JPAContainer<Sensor> sensors;
	private EntityManager em;

	public SensorsView() {
		super();
		configureComponents();
		buildLayout();
	}

	private void configureComponents() {
		try {
			EmProducer pr = (EmProducer) new InitialContext().lookup("java:module/" + EmProducer.class.getSimpleName());
			em = pr.getEM();
		} catch (NamingException | EJBAccessException e) {
			Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
		}
		
		// Create a persistent person container
		sensors = JPAContainerFactory.make(Sensor.class, em);
		try {
			MyEntityProviderBean entityProvider = (MyEntityProviderBean) new InitialContext().lookup("java:module/" + MyEntityProviderBean.class.getSimpleName());
			getSensors().setEntityProvider(entityProvider);
		} catch (NamingException | EJBAccessException e) {
			Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
		}
		
		// Bind it to a component
//		personTable = new Table("Список заказ-нарядов", persons);
//		personTable.setVisibleColumns("sensorId", "name", "maxTemp");
//		layout.addComponent(personTable);
		

		newContact.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -4761338363278949750L;

			@Override
			public void buttonClick(ClickEvent event) {
				contactForm.edit(new Sensor());
			}
		});

		filter.setInputPrompt("Filter contacts...");
		filter.addTextChangeListener(new TextChangeListener() {

			private static final long serialVersionUID = 8724327598558636541L;

			@Override
			public void textChange(TextChangeEvent event) {
				refreshContacts(event.getText());

			}
		});

		contactList
				.setContainerDataSource(new BeanItemContainer<>(Sensor.class));
		contactList.setColumnOrder("sensorId", "name", "maxTemp");
		contactList.removeColumn("id");
		contactList.removeColumn("gpioPin");
		contactList.setSelectionMode(Grid.SelectionMode.SINGLE);
		contactList.addSelectionListener(new SelectionListener() {

			private static final long serialVersionUID = -1852701286958204444L;

			@Override
			public void select(SelectionEvent event) {
				Long itemId = (Long) contactList.getSelectedRow();
				if(itemId!=null){
					contactForm.edit(sensors.getItem(itemId).getEntity());	
				}else {
					contactForm.edit(null);
				}
				
//				contactForm.edit((Sensor) contactList.getSelectedRow());
			}
		});
		refreshContacts();
	}

	private void buildLayout() {
		HorizontalLayout actions = new HorizontalLayout(filter, newContact);
		actions.setWidth("100%");
		filter.setWidth("100%");
		actions.setExpandRatio(filter, 1);

		VerticalLayout left = new VerticalLayout(actions, contactList);
		left.setSizeFull();
		contactList.setSizeFull();
		left.setExpandRatio(contactList, 1);

		HorizontalLayout mainLayout = new HorizontalLayout(left, contactForm);
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(left, 1);

		// Split and allow resizing
		// setContent(mainLayout);
		layout.addComponent(mainLayout);
	}

	public void refreshContacts() {
		refreshContacts(filter.getValue());
	}

	private void refreshContacts(String stringFilter) {
		// TODO вставить фильтрацию
		contactList.setContainerDataSource(sensors);
//		contactList.setContainerDataSource(new BeanItemContainer<>(
//				Sensor.class, service.findAll(stringFilter)));
		contactForm.setVisible(false);
	}

	public JPAContainer<Sensor> getSensors() {
		return sensors;
	}

}
