package com.obductiongame.translate.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.obductiongame.translate.shared.Language;

@RemoteServiceRelativePath("dialogue")
public interface DialogueService extends RemoteService {

	Language[] getLanguages();

}
