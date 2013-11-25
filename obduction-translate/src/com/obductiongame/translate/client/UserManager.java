package com.obductiongame.translate.client;

import com.google.gwt.core.client.GWT;
import com.obductiongame.translate.client.ErrorHandler.ErrorAsyncCallback;
import com.obductiongame.translate.client.service.MainServiceAsync;
import com.obductiongame.translate.shared.LoginInfo;

public class UserManager {

	private MainServiceAsync service;
	private MainLayout layout;

	public UserManager(MainLayout layout, ClientFactory clientFactory) {
		super();
		this.layout = layout;
		service = clientFactory.getMainService();
	}

	public void checkIfLoggedIn() {
		service.getLoginInfo(GWT.getHostPageBaseURL(), new ErrorAsyncCallback<LoginInfo>(UserManager.class.getName(), "Loading login info") {
			@Override
			public void onSuccess(LoginInfo result) {
				if (result.isLoggedIn()) {
					layout.setUserName(result.getName());
					layout.setLogoutLink(result.getLogoutUrl());
				} else {
					layout.setUserName("Anonymous");
					layout.setLoginLink(result.getLoginUrl());
				}
			}
		});
	}

}
