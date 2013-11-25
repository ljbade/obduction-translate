package com.obductiongame.translate.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.XsrfTokenServiceAsync;
import com.google.web.bindery.event.shared.EventBus;
import com.obductiongame.translate.client.request.DialogueLineRequest;
import com.obductiongame.translate.client.request.RegisteredUserRequest;
import com.obductiongame.translate.client.service.MainServiceAsync;
import com.obductiongame.translate.client.view.EditView;

public interface ClientFactory {
	EventBus getEventBus();
	PlaceController getPlaceController();

	XsrfTokenServiceAsync getXsrfService();
	MainServiceAsync getMainService();

	DialogueLineRequest getDialogueLineRequest();
	RegisteredUserRequest getRegisteredUserRequest();

	EditView getEditView();
}
