package com.obductiongame.translate.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.obductiongame.translate.server.entity.UserRequiredEntity;
import com.obductiongame.translate.server.locator.EntityLocator;

@ProxyFor(value = UserRequiredEntity.class, locator = EntityLocator.class)
public interface UserRequiredProxy extends EntityProxy {

	RegisteredUserProxy getUser();
	void setUser(RegisteredUserProxy user);

}
