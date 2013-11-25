package com.obductiongame.translate.client;

import com.google.gwt.core.client.GWT;
import com.obductiongame.translate.client.ErrorHandler.ErrorAsyncCallback;
import com.obductiongame.translate.client.ErrorHandler.ErrorReceiver;
import com.obductiongame.translate.client.proxy.RegisteredUserProxy;
import com.obductiongame.translate.client.request.RegisteredUserRequest;
import com.obductiongame.translate.client.service.MainServiceAsync;
import com.obductiongame.translate.shared.LoginInfo;

public class UserManagerImpl implements UserManager {

	//private ClientFactory clientFactory;
	//private MainServiceAsync service;
	private MainLayout layout;

	private LoginInfo loginInfo;
	private RegisteredUserProxy user;

	public UserManagerImpl(/*ClientFactory clientFactory*/) {
		super();
		//this.clientFactory = clientFactory;
		//service = clientFactory.getMainService();
	}

	@Override
	public void checkIfLoggedIn(final ClientFactory clientFactory) {
		MainServiceAsync service = clientFactory.getMainService();
		service.getLoginInfo(GWT.getHostPageBaseURL(), new ErrorAsyncCallback<LoginInfo>(UserManagerImpl.class.getName(), "Loading login info") {
			@Override
			public void onSuccess(LoginInfo result) {
				loginInfo = result;

				if (result.isLoggedIn()) {
					layout.setUserName(loginInfo.getName());
					layout.setLogoutLink(loginInfo.getLogoutUrl());

					RegisteredUserRequest req = clientFactory.getRegisteredUserRequest();
					req.get().fire(new ErrorReceiver<RegisteredUserProxy>(UserManagerImpl.class.getName(), "Loading the user profile") {
						@Override
						public void onSuccess(RegisteredUserProxy response) {
							if (response == null) {
								RegisteredUserRequest req = clientFactory.getRegisteredUserRequest();
								final RegisteredUserProxy newUser = req.create(RegisteredUserProxy.class);
								req.put(newUser).fire(new ErrorReceiver<Void>(UserManagerImpl.class.getName(), "Creating the user profile") {
									@Override
									public void onSuccess(Void response) {
										loadProfile(newUser);
									}
								});
							} else {
								loadProfile(response);
							}
						}
					});
				} else {
					layout.setUserName("Anonymous");
					layout.setLoginLink(loginInfo.getLoginUrl());
				}
			}
		});
	}

	private void loadProfile(RegisteredUserProxy user) {
		this.user = user;
	}

	@Override
	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	@Override
	public RegisteredUserProxy getUser() {
		return user;
	}

	// TODO: replace with event bus, use event bus to log user/in out from layout instead of this
	@Override
	public void setMainLayout(MainLayout layout) {
		this.layout = layout;
	}

}
