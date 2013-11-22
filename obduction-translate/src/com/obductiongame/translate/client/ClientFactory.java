package com.obductiongame.translate.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.rpc.XsrfTokenServiceAsync;
import com.google.web.bindery.event.shared.EventBus;

public interface ClientFactory {
	EventBus getEventBus();
	PlaceController getPlaceController();

	XsrfTokenServiceAsync getXsrfService();
	DialogueServiceAsync getDialogueService();

	EditView getEditView();
}
