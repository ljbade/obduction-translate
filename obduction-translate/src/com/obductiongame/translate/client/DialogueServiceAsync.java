package com.obductiongame.translate.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.obductiongame.translate.shared.DialogueLine;

public interface DialogueServiceAsync {

	void getLines(AsyncCallback<DialogueLine[]> callback);
	void addLine(DialogueLine line, AsyncCallback<Void> callback);
	void deleteLine(Long id, AsyncCallback<Void> callback);

}
