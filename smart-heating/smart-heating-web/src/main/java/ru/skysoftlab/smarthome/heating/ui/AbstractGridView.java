package ru.skysoftlab.smarthome.heating.ui;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;

import ru.skysoftlab.smarthome.heating.ejb.EmProducer;
import ru.skysoftlab.smarthome.heating.ui.forms.AbstractForm;

import com.vaadin.addon.jpacontainer.EntityProvider;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Container.Indexed;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public abstract class AbstractGridView<T, F extends AbstractForm<T>, P extends EntityProvider<T>>
		extends BaseMenuView {

	private static final long serialVersionUID = 4734617922366685803L;

	private Class<T> clazz;
	private Class<P> entityProviderClass;
	private Class<F> formClass;

	private TextField filter = new TextField();
	private Grid grid = new Grid();
	private Button newEntityButton = new Button(getNewButtonLabel());
	private F entityForm;

	private JPAContainer<T> jpaContainer;
	private EntityManager em;

	public AbstractGridView(Class<T> clazz, Class<F> formClass,
			Class<P> entityProviderClass) {
		super();
		this.clazz = clazz;
		this.entityProviderClass = entityProviderClass;
		this.formClass = formClass;
		configureComponents();
		buildLayout();
	}

	protected abstract String getNewButtonLabel();

	public JPAContainer<T> getJpaContainer() {
		return jpaContainer;
	}

	public Grid getGrid() {
		return grid;
	}

	private void configureComponents() {
		try {
			entityForm = this.formClass.newInstance();
			entityForm.setGridView(this);
			em = lookupBean(EmProducer.class).getEM();
			// Create a persistent person container
			jpaContainer = JPAContainerFactory.make(clazz, em);
			jpaContainer
					.setEntityProvider(lookupBean(this.entityProviderClass));
		} catch (Exception e) {
			Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
		}

		newEntityButton.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -4761338363278949750L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					entityForm.edit(clazz.newInstance());
				} catch (InstantiationException | IllegalAccessException e) {
					Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
				}
			}
		});

		filter.setInputPrompt("Filter contacts...");
		filter.addTextChangeListener(new TextChangeListener() {

			private static final long serialVersionUID = 8724327598558636541L;

			@Override
			public void textChange(TextChangeEvent event) {
				refreshData(event.getText());
			}
		});

		grid.setContainerDataSource(new BeanItemContainer<>(clazz));
		grid.setColumnOrder(getColumnOrder());
		for (Object columnId : getRemoveColumn()) {
			grid.removeColumn(columnId);
		}
		grid.setSelectionMode(Grid.SelectionMode.SINGLE);
		grid.addSelectionListener(new SelectionListener() {

			private static final long serialVersionUID = -1852701286958204444L;

			@Override
			public void select(SelectionEvent event) {
				Long itemId = (Long) grid.getSelectedRow();
				if (itemId != null) {
					entityForm.edit(jpaContainer.getItem(itemId).getEntity());
				} else {
					entityForm.edit(null);
				}
				// contactForm.edit((Sensor) contactList.getSelectedRow());
			}
		});
		refreshData();
	}

	/**
	 * Колонки для скрытия.
	 * 
	 * @return
	 */
	protected abstract Object[] getRemoveColumn();

	/**
	 * Идентификаторы колонок.
	 * 
	 * @return
	 */
	protected abstract Object[] getColumnOrder();

	private void buildLayout() {
		HorizontalLayout actions = new HorizontalLayout(filter, newEntityButton);
		actions.setSpacing(true);
		actions.setMargin(new MarginInfo(true, true, true, false));
		actions.setSizeUndefined();
		actions.setWidth("100%");
		filter.setWidth("100%");
		actions.setExpandRatio(filter, 1);

		VerticalLayout left = new VerticalLayout(actions, grid);
		left.setSizeFull();
		grid.setSizeFull();
		left.setExpandRatio(grid, 1);

		HorizontalLayout mainLayout = new HorizontalLayout(left, entityForm);
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(left, 1);

		// Split and allow resizing
		// setContent(mainLayout);
		layout.addComponent(mainLayout);
		// layout.setComponentAlignment(mainLayout, Alignment.TOP_CENTER);
	}

	public void refreshData() {
		grid.setContainerDataSource(refreshData(filter.getValue()));
		entityForm.setVisible(false);
	}

	protected abstract Indexed refreshData(String value);

	@SuppressWarnings("unchecked")
	protected <B> B lookupBean(Class<B> beanClass) throws NamingException {
		return (B) new InitialContext().lookup("java:module/"
				+ beanClass.getSimpleName());
	}
}
