package ru.skysoftlab.smarthome.heating.ui.impl;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBAccessException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ru.skysoftlab.smarthome.heating.NavigationService;
import ru.skysoftlab.smarthome.heating.ejb.TestBean;
import ru.skysoftlab.smarthome.heating.ui.BaseMenuView;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

@CDIView(NavigationService.MAIN)
//@RolesAllowed("ADMIN")
public class MainView extends BaseMenuView {
	
	private static final long serialVersionUID = 4030871317601600678L;

	Label text = new Label();

    Button protectedinfo = new Button("Get protected information", new Button.ClickListener() {
		private static final long serialVersionUID = 5800978668706508358L;
		@Override
        public void buttonClick(ClickEvent event) {
			try {
				TestBean testBean = (TestBean) new InitialContext().lookup("java:module/" + TestBean.class.getSimpleName());
				text.setValue(testBean.getProtectedInfo());
			} catch (NamingException | EJBAccessException e) {
				Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
			}
        }
    });
    
    @PostConstruct
    public void init(){
    	HorizontalLayout fields = new HorizontalLayout(text, protectedinfo);
		fields.setSpacing(true);
		fields.setMargin(new MarginInfo(true, true, true, false));
		fields.setSizeUndefined();
		layout.addComponent(fields);
		layout.setComponentAlignment(fields, Alignment.TOP_CENTER);
    }

	@Override
	public void enter(ViewChangeEvent event) {
		String username = String.valueOf(getSession().getAttribute("user"));
		text.setValue("Hello " + username);
	}
}
