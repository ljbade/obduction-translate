package com.obductiongame.translate.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;
import com.obductiongame.translate.client.ErrorHandler.ErrorExceptionHandler;
import com.obductiongame.translate.client.view.EditPlace;

public class MainModule implements EntryPoint {
	//TODO: use cell table!
	//TODO: use ui binder
	// TODO: use ui editors
	//TODO:jsr 303
	//TODO: command pattern rpc/event bus
	//TODO: use xsrf/safehtml
	//TODO:collate into todo file

	private final ClientFactory clientFactory = GWT.create(ClientFactory.class);

	private final Place defaultPlace = new EditPlace("");
	private final EventBus eventBus = clientFactory.getEventBus();
	private final PlaceController placeController = clientFactory.getPlaceController();

	private final ActivityMapper activityMapper = new MainActivityMapper(clientFactory);
	private final ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);

	private final PlaceHistoryMapper historyMapper= GWT.create(MainPlaceHistoryMapper.class);
	private final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);

	private final DockLayoutPanel layoutPanel = new DockLayoutPanel(Unit.EM);
	private final SimplePanel activityPanel = new SimplePanel();

	public void onModuleLoad() {
		// Set a custom exception handler
		GWT.setUncaughtExceptionHandler(new ErrorExceptionHandler());

		// Workaround for uncaught exceptions
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			public void execute() {
				onModuleLoad2();
			}
		});
	}

	private void onModuleLoad2() {
		// Start ActivityManager for the main widget with our ActivityMapper
		activityManager.setDisplay(activityPanel);

		// Start PlaceHistoryHandler with our PlaceHistoryMapper
		historyHandler.register(placeController, eventBus, defaultPlace);

		// Goes to the place represented on URL else default place
		historyHandler.handleCurrentHistory();

		// Create the layout
		layoutPanel.addNorth(new HTML("<h1>Obduction Translate</h1><h2>Edit</h2>"), 8);//TODO:replace with ui binder
		layoutPanel.addSouth(new Label("Footer"), 2);
		layoutPanel.addWest(new Label("Menu"), 6);
		layoutPanel.add(activityPanel);
		RootLayoutPanel.get().add(layoutPanel);
	}

}
