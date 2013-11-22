package com.obductiongame.translate.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.rpc.XsrfTokenService;
import com.google.gwt.user.client.rpc.XsrfTokenServiceAsync;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class ClientFactoryImpl implements ClientFactory {

	private static final Logger LOG = Logger.getLogger(ClientFactoryImpl.class.getName());

	private final EventBus eventBus = new SimpleEventBus();
	private final PlaceController placeController = new PlaceController(eventBus);

	private final XsrfTokenServiceAsync xsrfService = (XsrfTokenServiceAsync)GWT.create(XsrfTokenService.class);
	private final DialogueServiceAsync dialogueService = GWT.create(DialogueService.class);

	private final EditView editView = new EditViewImpl();

	public ClientFactoryImpl() {
		LOG.log(Level.INFO, "ClientFactoryImpl(): called");
		((ServiceDefTarget)xsrfService).setServiceEntryPoint(GWT.getModuleBaseURL() + "xsrf");
	}

	@Override
	public EventBus getEventBus() {
		LOG.log(Level.INFO, "getEventBus(): called");
		return eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		LOG.log(Level.INFO, "getPlaceController(): called");
		return placeController;
	}

	@Override
	public XsrfTokenServiceAsync getXsrfService() {
		LOG.log(Level.INFO, "getXsrfService(): called");
		return xsrfService;
	}

	@Override
	public DialogueServiceAsync getDialogueService() {
		LOG.log(Level.INFO, "getDialogueService(): called");
		return dialogueService;
	}

	@Override
	public EditView getEditView() {
		LOG.log(Level.INFO, "getEditView(): called");
		return editView;
	}

}
