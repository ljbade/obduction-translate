package com.obductiongame.translate.server.locator;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.web.bindery.requestfactory.shared.ServiceLocator;

public class MainServiceLocator implements ServiceLocator {

	private static final Logger LOG = Logger.getLogger(MainServiceLocator.class.getName());

	@Override
	public Object getInstance(Class<?> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			LOG.log(Level.SEVERE, "Unable to create the service.", e);
			throw new RuntimeException("Unable to create the service.", e);
		}
	}

}
