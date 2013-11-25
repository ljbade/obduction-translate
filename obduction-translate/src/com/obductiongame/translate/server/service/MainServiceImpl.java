package com.obductiongame.translate.server.service;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.XsrfProtectedServiceServlet;
import com.obductiongame.translate.client.service.MainService;
import com.obductiongame.translate.server.LanguageLoader;
import com.obductiongame.translate.shared.Language;
import com.obductiongame.translate.shared.LoginInfo;

@SuppressWarnings("serial")
public class MainServiceImpl extends XsrfProtectedServiceServlet implements
		MainService {

	@Override
	public Language[] getLanguages() {
		return LanguageLoader.getLanguages();
	}

	@Override
	public LoginInfo getLoginInfo(String requestUrl) {
		UserService userService = UserServiceFactory.getUserService();
		LoginInfo loginInfo;

		if (userService.isUserLoggedIn()) {
			User user = userService.getCurrentUser();
			loginInfo = new LoginInfo(userService.isUserAdmin(), userService.createLogoutURL(requestUrl), user.getNickname(), user.getEmail());
		} else {
			loginInfo = new LoginInfo(userService.createLoginURL(requestUrl));
		}

		return loginInfo;
	}

}
