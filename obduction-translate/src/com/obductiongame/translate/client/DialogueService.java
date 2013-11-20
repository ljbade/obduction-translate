package com.obductiongame.translate.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.obductiongame.translate.shared.DialogueLine;

@RemoteServiceRelativePath("dialogue")
public interface DialogueService extends RemoteService{

	DialogueLine[] getLines();
	void addLine(DialogueLine line) throws Exception;
	void deleteLine(Long id) throws Exception;

}
