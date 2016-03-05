package ru.skysoftlab.smarthome.heating;

import javax.inject.Inject;

import com.vaadin.cdi.access.AccessControl;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ErrorView extends CustomComponent implements View {
	
	private static final long serialVersionUID = 1L;

    @Inject
    private AccessControl accessControl;

    @Inject
    private javax.enterprise.event.Event<NavigationEvent> navigationEvent;

    @Override
    public void enter(ViewChangeEvent event) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
        layout.setSpacing(true);

        layout.addComponent(new Label(
                "Unfortunately, the page you've requested does not exists."));
        if (accessControl.isUserSignedIn()) {
            layout.addComponent(createChatButton());
        } else {
            layout.addComponent(createLoginButton());
        }
        setCompositionRoot(layout);
    }

    private Button createLoginButton() {
        Button button = new Button("To login page");
        button.addClickListener(new ClickListener() {
        	
        	private static final long serialVersionUID = 1L;
        	
            @Override
            public void buttonClick(ClickEvent event) {
                navigationEvent.fire(new NavigationEvent(NavigationService.LOGIN));
            }
        });
        return button;
    }

    private Button createChatButton() {
        Button button = new Button("Back to the main page");
        button.addClickListener(new ClickListener() {
        	
        	private static final long serialVersionUID = 1L;
        	
            @Override
            public void buttonClick(ClickEvent event) {
                navigationEvent.fire(new NavigationEvent(NavigationService.MAIN));
            }
        });
        return button;
    }
}
