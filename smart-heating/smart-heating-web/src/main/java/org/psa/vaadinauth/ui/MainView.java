package org.psa.vaadinauth.ui;

import javax.ejb.EJBAccessException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.psa.vaadinauth.MainUI;
import org.psa.vaadinauth.ejb.TestBean;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class MainView extends CustomComponent implements View {
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
		setSizeFull();

    	HorizontalLayout fields = new HorizontalLayout(text, protectedinfo, logout);
		fields.setSpacing(true);
		fields.setMargin(new MarginInfo(true, true, true, false));
		fields.setSizeUndefined();

		VerticalLayout viewLayout = new VerticalLayout(fields);
		viewLayout.setSizeFull();
		viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
		viewLayout.setStyleName(Reindeer.LAYOUT_BLACK);
        setCompositionRoot(viewLayout);
    }

	@Override
	public void enter(ViewChangeEvent event) {
		String username = String.valueOf(getSession().getAttribute("user"));
		text.setValue("Hello " + username);
	}
}
