package com.obductiongame.translate.client.request;

import com.google.web.bindery.requestfactory.shared.RequestFactory;

public interface MainRequestFactory extends RequestFactory {

	DialogueLineRequest dialogueLineRequest();
	RegisteredUserRequest registeredUserRequest();

}
