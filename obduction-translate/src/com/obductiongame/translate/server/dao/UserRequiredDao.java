package com.obductiongame.translate.server.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.obductiongame.translate.server.NotLoggedInException;
import com.obductiongame.translate.server.ServiceException;
import com.obductiongame.translate.server.WrongUserException;
import com.obductiongame.translate.server.entity.UserRequiredEntity;

public abstract class UserRequiredDao<T extends UserRequiredEntity> extends EntityDao<T> {

	private static final Logger LOG = Logger.getLogger(EntityDao.class.getName());

	protected UserRequiredDao(Class<T> clazz) {
		super(clazz);
	}

	@Override
	public long count() throws ServiceException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public T get(long id) throws ServiceException {
		return get(KeyFactory.createKey(clazz.getSimpleName(), id));
	}

	@Override
	public T get(String name) throws ServiceException {
		return get(KeyFactory.createKey(getUsersKey(), clazz.getSimpleName(), name));
	}

	@Override
	public T get(Key key) throws ServiceException {
		requireUser(key);
		return super.get(key);
	}

	@Override
	public List<T> getAll() throws ServiceException {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void put(T entity) throws ServiceException {
		requireUser(entity);
		super.put(entity);
	}

	@Override
	public void delete(T entity) throws ServiceException {
		requireUser(entity);
		super.delete(entity);
	}

	protected void requireUser(UserRequiredEntity entity) throws WrongUserException, NotLoggedInException {
		requireUser(entity.getKey());
	}

	protected void requireUser(Key key) throws WrongUserException, NotLoggedInException {
		if (!key.getParent().equals(getUsersKey())) {
			LOG.log(Level.SEVERE, "The entity's owner is not the user currently logged in with key " + key);
			throw new WrongUserException();
		}
	}
}
