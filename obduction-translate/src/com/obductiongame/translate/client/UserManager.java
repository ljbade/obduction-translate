package com.obductiongame.translate.client;

import com.obductiongame.translate.client.proxy.RegisteredUserProxy;
import com.obductiongame.translate.shared.LoginInfo;

public interface UserManager {

	public void setMainLayout(MainLayout layout);
	public void checkIfLoggedIn();
	public LoginInfo getLoginInfo();
	public RegisteredUserProxy getUser();

}