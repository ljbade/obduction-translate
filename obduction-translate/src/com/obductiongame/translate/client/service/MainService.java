package com.obductiongame.translate.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.obductiongame.translate.shared.Language;
import com.obductiongame.translate.shared.LoginInfo;

@RemoteServiceRelativePath("service")
public interface MainService extends RemoteService {

	Language[] getLanguages();
	LoginInfo getLoginInfo(String requestUrl);

}
