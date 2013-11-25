package com.obductiongame.translate.server.locator;

import com.google.web.bindery.requestfactory.shared.Locator;
import com.obductiongame.translate.server.dao.DialogueLineDao;
import com.obductiongame.translate.server.entity.Entity;

public class EntityLocator extends Locator<Entity, String> {

	@Override
	public Entity create(Class<? extends Entity> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Entity find(Class<? extends Entity> clazz, String id) {
		return new DialogueLineDao().get(id);//TODO
	}

	@Override
	public Class<Entity> getDomainType() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getId(Entity domainObject) {
		return domainObject.getKey();
	}

	@Override
	public Class<String> getIdType() {
		return String.class;
	}

	@Override
	public Object getVersion(Entity domainObject) {
		return domainObject.getVersion();
	}

}
