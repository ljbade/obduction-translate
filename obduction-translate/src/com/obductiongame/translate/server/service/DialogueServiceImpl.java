package com.obductiongame.translate.server.service;

import com.google.gwt.user.server.rpc.XsrfProtectedServiceServlet;
import com.obductiongame.translate.client.service.DialogueService;
import com.obductiongame.translate.server.LanguageLoader;
import com.obductiongame.translate.shared.Language;

@SuppressWarnings("serial")
public class DialogueServiceImpl extends XsrfProtectedServiceServlet implements
		DialogueService {

	@Override
	public Language[] getLanguages() {
		return LanguageLoader.getLanguages();
	}

}
