package com.obductiongame.translate.client;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.rpc.RpcToken.RpcTokenImplementation;
import com.google.gwt.user.client.rpc.XsrfProtectedService;
import com.obductiongame.translate.shared.DialogueLine;
import com.obductiongame.translate.shared.Language;

@RemoteServiceRelativePath("dialogue")
@RpcTokenImplementation("com.google.gwt.user.client.rpc.XsrfToken")
public interface DialogueService extends XsrfProtectedService {

	DialogueLine[] getLines(); // TODO: exceptions on all?
	String addLine(DialogueLine line) throws Exception;
	void deleteLine(String key) throws Exception;
	Language[] getLanguages();//don't really want xsrf on this for caching

}
