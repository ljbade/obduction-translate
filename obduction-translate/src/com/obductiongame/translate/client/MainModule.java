package com.obductiongame.translate.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.HasRpcToken;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.rpc.XsrfToken;
import com.google.gwt.user.client.rpc.XsrfTokenServiceAsync;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;

public class MainModule implements EntryPoint {// TODO:http://www.google.com/events/io/2009/sessions/GoogleWebToolkitBestPractices.html
// TODO: use gwt 2.0 layouts
	//TODO: use cell table!
	//TODO: use ui binder
	// TODO: use ui editors
	// TODO: use request factory
	//TODO:jsr 303
	//TODO: command pattern rpc/event bus
	// TODO: remove unnused libs and import modules
	private static final Logger LOG = Logger.getLogger(MainModule.class.getName());

	private final ClientFactory clientFactory = GWT.create(ClientFactory.class);

	private final Place defaultPlace = new EditPlace("");
	private final EventBus eventBus = clientFactory.getEventBus();
	private final PlaceController placeController = clientFactory.getPlaceController();

	private final ActivityMapper activityMapper = new MainActivityMapper(clientFactory);
	private final ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);

	private final PlaceHistoryMapper historyMapper= GWT.create(MainPlaceHistoryMapper.class);
	private final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);

	private final XsrfTokenServiceAsync xsrfService = clientFactory.getXsrfService();

	private final DockLayoutPanel layoutPanel = new DockLayoutPanel(Unit.EM);
	private final SimplePanel activityPanel = new SimplePanel();

	public void onModuleLoad() {
		LOG.log(Level.INFO, "onModuleLoad(): called");

		// Load the XSRF service
		((ServiceDefTarget)xsrfService).setServiceEntryPoint(GWT.getModuleBaseURL() + "xsrf");
		xsrfService.getNewXsrfToken(new AsyncCallback<XsrfToken>() {
			@Override
			public void onSuccess(XsrfToken token) {
				// Load the services
				((HasRpcToken) clientFactory.getDialogueService()).setRpcToken(token);

				onServicesLoaded();
			}

			@Override
			public void onFailure(Throwable caught) {//TODO: better error handling
				//TODO:HasRpcToken.setRpcTokenExceptionHandler()
				LOG.log(Level.SEVERE, "getNewXsrfToken(): failure: " + caught.toString());
				Window.alert("Error: " + caught.toString());
			}
		});
	}

	private void onServicesLoaded() {
		// Start ActivityManager for the main widget with our ActivityMapper
		activityManager.setDisplay(activityPanel);

		// Start PlaceHistoryHandler with our PlaceHistoryMapper
		historyHandler.register(placeController, eventBus, defaultPlace);

		// Goes to the place represented on URL else default place
		historyHandler.handleCurrentHistory();

		// Create the layout
		layoutPanel.addNorth(new HTML("<h1>Obduction Translate</h1><h2>Edit</h2>"), 8);//TODO:replace with ui binder, safehtml
		layoutPanel.addSouth(new Label("Footer"), 2);
		layoutPanel.addWest(new Label("Menu"), 6);
		layoutPanel.add(activityPanel);
		RootLayoutPanel.get().add(layoutPanel);

		// Hide the loading bar
		//DOM.setStyleAttribute(RootPanel.get("loading").getElement(), "display", "none");
	}

}
