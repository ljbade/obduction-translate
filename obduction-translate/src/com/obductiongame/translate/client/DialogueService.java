package com.obductiongame.translate.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.obductiongame.translate.shared.DialogueLine;
import com.obductiongame.translate.shared.Language;

@RemoteServiceRelativePath("dialogue")
public interface DialogueService extends RemoteService{

	DialogueLine[] getLines(); // TODO: exceptions on all?
	String addLine(DialogueLine line) throws Exception;
	void deleteLine(String key) throws Exception;
	Language[] getLanguages();

}
