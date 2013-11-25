package com.obductiongame.translate.server.locator;

import com.google.web.bindery.requestfactory.shared.ServiceLocator;

public class MainServiceLocator implements ServiceLocator {

	@Override
	public Object getInstance(Class<?> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
