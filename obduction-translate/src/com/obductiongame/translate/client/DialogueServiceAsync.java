package com.obductiongame.translate.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.obductiongame.translate.shared.DialogueLine;
import com.obductiongame.translate.shared.Language;

public interface DialogueServiceAsync {

	void getLines(AsyncCallback<DialogueLine[]> callback);
	void addLine(DialogueLine line, AsyncCallback<String> callback);
	void deleteLine(String key, AsyncCallback<Void> callback);
	void getLanguages(AsyncCallback<Language[]> callback);

}
