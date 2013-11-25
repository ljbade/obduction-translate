package com.obductiongame.translate.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.obductiongame.translate.shared.Language;
import com.obductiongame.translate.shared.LoginInfo;

public interface MainServiceAsync {

	void getLanguages(AsyncCallback<Language[]> callback);
	void getLoginInfo(String requestUrl, AsyncCallback<LoginInfo> callback);

}
