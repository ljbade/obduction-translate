package com.obductiongame.translate.server.locator;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.Key;
import com.google.web.bindery.requestfactory.shared.Locator;
import com.obductiongame.translate.server.ServiceException;
import com.obductiongame.translate.server.dao.DialogueLineDao;
import com.obductiongame.translate.server.dao.RegisteredUserDao;
import com.obductiongame.translate.server.entity.DialogueLine;
import com.obductiongame.translate.server.entity.Entity;

public class EntityLocator extends Locator<Entity, Key> {

	private static final Logger LOG = Logger.getLogger(EntityLocator.class.getName());

	@Override
	public Entity create(Class<? extends Entity> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			LOG.log(Level.SEVERE, "Unable to create the entity.", e);
			throw new RuntimeException("Unable to create the entity.", e);
		}
	}

	@Override
	public Entity find(Class<? extends Entity> clazz, Key id) {
		try {
			if (clazz == DialogueLine.class) {
				return new DialogueLineDao().get(id);
			} else if (clazz == DialogueLine.class) {
				return new RegisteredUserDao().get(id);
			} else {
				LOG.log(Level.SEVERE, "That entity type is unknown.");
				throw new IllegalArgumentException("That entity type is unknown.");
			}
		} catch(ServiceException e) {
			LOG.log(Level.SEVERE, "Unable to get the entity.", e);
			throw new RuntimeException("Unable to get the entity.", e);
		}
	}

	@Override
	public Class<Entity> getDomainType() {
		LOG.log(Level.SEVERE, "This method should never be called.");
		throw new UnsupportedOperationException("This method should never be called.");
	}

	@Override
	public Key getId(Entity domainObject) {
		return domainObject.getKey();
	}

	@Override
	public Class<Key> getIdType() {
		return Key.class;
	}

	@Override
	public Object getVersion(Entity domainObject) {
		return domainObject.getVersion();
	}

}
