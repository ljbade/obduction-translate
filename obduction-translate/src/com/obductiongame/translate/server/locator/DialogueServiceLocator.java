package com.obductiongame.translate.server.locator;

import com.google.web.bindery.requestfactory.shared.ServiceLocator;
import com.obductiongame.translate.server.dao.DialogueLineDaoImpl;

public class DialogueServiceLocator implements ServiceLocator {

	@Override
	public Object getInstance(Class<?> clazz) {
		return new DialogueLineDaoImpl();
	}

}
