package com.obductiongame.translate.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.rpc.XsrfTokenService;
import com.google.gwt.user.client.rpc.XsrfTokenServiceAsync;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.obductiongame.translate.client.request.DialogueLineRequest;
import com.obductiongame.translate.client.request.MainRequestFactory;
import com.obductiongame.translate.client.request.RegisteredUserRequest;
import com.obductiongame.translate.client.service.MainService;
import com.obductiongame.translate.client.service.MainServiceAsync;
import com.obductiongame.translate.client.view.EditView;
import com.obductiongame.translate.client.view.EditViewImpl;

public class ClientFactoryImpl implements ClientFactory {

	private final EventBus eventBus = new SimpleEventBus();
	private final PlaceController placeController = new PlaceController(eventBus);

	private final MainRequestFactory requestFactory = GWT.create(MainRequestFactory.class);

	private final XsrfTokenServiceAsync xsrfService = (XsrfTokenServiceAsync)GWT.create(XsrfTokenService.class);
	private final MainServiceAsync mainService = GWT.create(MainService.class);

	// Must come after MainRequestFactory and MainServiceAsync
	private final UserManager userManager = new UserManagerImpl();

	private final EditView editView = new EditViewImpl();

	public ClientFactoryImpl() {
		super();

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
	public MainServiceAsync getMainService() {
		return mainService;
	}

	@Override
	public EditView getEditView() {
		return editView;
	}

	@Override
	public DialogueLineRequest getDialogueLineRequest() {
		return requestFactory.dialogueLineRequest();
	}

	@Override
	public RegisteredUserRequest getRegisteredUserRequest() {
		return requestFactory.registeredUserRequest();
	}

	@Override
	public UserManager getUserManager() {
		return userManager;
	}

}
