package com.obductiongame.translate.server.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

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
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			Query q = pm.newQuery("select key from " + clazz.getSimpleName());
			@SuppressWarnings("unchecked")
			List<T> entities = (List<T>) executeAsUser(q);
			return entities.size();
		} finally {
			pm.close();
		}
	}

	@Override
	public T get(long id) throws ServiceException {
		return get(KeyFactory.createKey(getUsersKey(), clazz.getSimpleName(), id));
	}

	@Override
	public T get(String name) throws ServiceException {
		return get(KeyFactory.createKey(getUsersKey(), clazz.getSimpleName(), name));
	}

	@Override
	public T get(Key key) throws ServiceException {
		requireUserAsParent(key);
		return super.get(key);
	}

	@Override
	public List<T> getAll() throws ServiceException {
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			Query q = pm.newQuery(clazz);
			@SuppressWarnings("unchecked")
			List<T> entities = (List<T>) executeAsUser(q);
			return entities;
		} finally {
			pm.close();
		}
	}

	@Override
	public void put(T entity) throws ServiceException {
		requireOrSetUserAsParent(entity);
		super.put(entity);
	}

	@Override
	public void delete(T entity) throws ServiceException {
		requireUserAsParent(entity);
		super.delete(entity);
	}

	protected Object executeAsUser(Query query) throws NotLoggedInException {
		//return executeAsUserWithArray(query);
		query.setFilter("userKey == currentUserKey");
		query.declareParameters(Key.class.getName() + " currentUserKey");
		return query.execute(getUsersKey());
	}

	protected void requireUserAsParent(UserRequiredEntity entity) throws WrongUserException, NotLoggedInException {
		if (entity.getUserKey() != null) {
			if (!entity.getUserKey().equals(getUsersKey())) {
				LOG.log(Level.SEVERE, "The entity's parent is not the user currently logged in");
				throw new WrongUserException();
			}
		} else {
			LOG.log(Level.SEVERE, "The entity's parent is not set");
			throw new WrongUserException();
		}
	}

	protected void requireUserAsParent(Key key) throws WrongUserException, NotLoggedInException {
		if (key != null) {
			if (!key.getParent().equals(getUsersKey())) {
				LOG.log(Level.SEVERE, "The key's parent is not the user currently logged in");
				throw new WrongUserException();
			}
		} else {
			LOG.log(Level.SEVERE, "The key's parent is not set");
			throw new WrongUserException();
		}
	}

	protected void requireOrSetUserAsParent(UserRequiredEntity entity) throws WrongUserException, NotLoggedInException {
		if (entity.getUserKey() == null) {
			entity.setUserKey(getUsersKey());
		} else {
			requireUserAsParent(entity);
		}
	}

	protected void requireOrSetUserAsParent(UserRequiredEntity entity, Class<? extends UserRequiredEntity> clazz, String name) throws WrongUserException, NotLoggedInException {
		if (entity.getUserKey() == null) {
			entity.setKey(getUsersKey().getChild(clazz.getSimpleName(), name));
		} else {
			requireUserAsParent(entity);
		}
	}

	protected void requireOrSetUserAsParent(UserRequiredEntity entity, Class<? extends UserRequiredEntity> clazz, long id) throws WrongUserException, NotLoggedInException {
		if (entity.getUserKey() == null) {
			entity.setKey(getUsersKey().getChild(clazz.getSimpleName(), id));
		} else {
			requireUserAsParent(entity);
		}
	}

}
