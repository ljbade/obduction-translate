package com.obductiongame.translate.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.obductiongame.translate.shared.Language;

public interface DialogueServiceAsync {

	void getLanguages(AsyncCallback<Language[]> callback);

}
