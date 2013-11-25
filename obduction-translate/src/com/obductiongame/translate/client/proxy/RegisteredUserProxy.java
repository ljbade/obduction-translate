package com.obductiongame.translate.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.obductiongame.translate.server.entity.RegisteredUser;
import com.obductiongame.translate.server.locator.EntityLocator;

@ProxyFor(value = RegisteredUser.class, locator = EntityLocator.class)
public interface RegisteredUserProxy extends EntityProxy {

	String getId();
	void setId(String id);

}
