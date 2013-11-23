package com.obductiongame.translate.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.rpc.XsrfTokenService;
import com.google.gwt.user.client.rpc.XsrfTokenServiceAsync;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.obductiongame.translate.client.request.DialogueLineRequest;
import com.obductiongame.translate.client.request.DialogueRequestFactory;
import com.obductiongame.translate.client.service.DialogueService;
import com.obductiongame.translate.client.service.DialogueServiceAsync;
import com.obductiongame.translate.client.view.EditView;
import com.obductiongame.translate.client.view.EditViewImpl;

public class ClientFactoryImpl implements ClientFactory {

	private final EventBus eventBus = new SimpleEventBus();
	private final PlaceController placeController = new PlaceController(eventBus);

	private final DialogueRequestFactory requestFactory = GWT.create(DialogueRequestFactory.class);

	private final XsrfTokenServiceAsync xsrfService = (XsrfTokenServiceAsync)GWT.create(XsrfTokenService.class);
	private final DialogueServiceAsync dialogueService = GWT.create(DialogueService.class);

	private final EditView editView = new EditViewImpl();

	public ClientFactoryImpl() {
		// Start XSRF service
		((ServiceDefTarget)xsrfService).setServiceEntryPoint(GWT.getModuleBaseURL() + "xsrf");

		// Start RequestFactory
		requestFactory.initialize(eventBus, new CustomRequestTransport());
	}

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public XsrfTokenServiceAsync getXsrfService() {
		return xsrfService;
	}

	@Override
	public DialogueServiceAsync getDialogueService() {
		return dialogueService;
	}

	@Override
	public EditView getEditView() {
		return editView;
	}

	@Override
	public DialogueLineRequest getDialogueLineRequest() {
		return requestFactory.dialogueLineRequest();
	}

}
