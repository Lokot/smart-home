package ru.skysoftlab.smarthome.heating.ui;

import javax.ejb.EJBAccessException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.skysoftlab.smarthome.heating.MainUI;
import ru.skysoftlab.smarthome.heating.ejb.TestBean;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

public class MainView extends BaseMenuView {
	
	private static final long serialVersionUID = 4030871317601600678L;

	public static final String NAME = "";

    Label text = new Label();

    Button logout = new Button("Logout", new Button.ClickListener() {
		private static final long serialVersionUID = 5800978668706508358L;
		@Override
        public void buttonClick(ClickEvent event) {
			MainUI mainUI = (MainUI) UI.getCurrent();
			mainUI.getAuthenticator().logout((HttpServletRequest) VaadinService.getCurrentRequest());
            getSession().setAttribute("user", null);
            mainUI.getNavigator().navigateTo(NAME);
        }
    });

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
    
    public MainView() {
    	super();
		HorizontalLayout fields = new HorizontalLayout(text, protectedinfo, logout);
		fields.setSpacing(true);
		fields.setMargin(new MarginInfo(true, true, true, false));
		fields.setSizeUndefined();
		layout.addComponent(fields);
		layout.setComponentAlignment(fields, Alignment.TOP_CENTER);
//		VerticalLayout viewLayout = new VerticalLayout(fields);
//		viewLayout.setSizeFull();
//		viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
//		viewLayout.setStyleName(Reindeer.LAYOUT_BLACK);
//      setCompositionRoot(viewLayout);
    }

	@Override
	public void enter(ViewChangeEvent event) {
		String username = String.valueOf(getSession().getAttribute("user"));
		text.setValue("Hello " + username);
	}
}
