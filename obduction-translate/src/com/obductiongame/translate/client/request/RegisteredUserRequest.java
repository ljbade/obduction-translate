package com.obductiongame.translate.client.request;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.obductiongame.translate.client.proxy.RegisteredUserProxy;
import com.obductiongame.translate.server.dao.RegisteredUserDao;
import com.obductiongame.translate.server.locator.MainServiceLocator;

@Service(value = RegisteredUserDao.class, locator = MainServiceLocator.class)
public interface RegisteredUserRequest extends RequestContext {

	Request<RegisteredUserProxy> get();
	Request<Void> put(RegisteredUserProxy user); // Needed to work around bug (https://code.google.com/p/google-web-toolkit/issues/detail?id=6794)

}
