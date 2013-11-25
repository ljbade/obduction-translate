package com.obductiongame.translate.client.proxy;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.obductiongame.translate.server.entity.Entity;
import com.obductiongame.translate.server.locator.EntityLocator;

@ProxyFor(value = Entity.class, locator = EntityLocator.class)
public interface EntityProxy extends com.google.web.bindery.requestfactory.shared.EntityProxy {

	String getEncodedKey();
	void setEncodedKey(String encodedKey);
	long getVersion();
	void setVersion(long version);

	//TODO: registered user proxy with user
}
