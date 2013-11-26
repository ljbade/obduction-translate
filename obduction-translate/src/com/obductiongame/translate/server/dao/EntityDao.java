package com.obductiongame.translate.server.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.obductiongame.translate.server.NotLoggedInException;
import com.obductiongame.translate.server.ServiceException;
import com.obductiongame.translate.server.entity.Entity;
import com.obductiongame.translate.server.entity.RegisteredUser;

public abstract class EntityDao<T extends Entity> {

	private static final Logger LOG = Logger.getLogger(EntityDao.class.getName());
	protected static final PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");

	protected Class<T> clazz;

	protected EntityDao(Class<T> clazz) {
		this.clazz = clazz;
	}

	public long count() throws ServiceException {
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			Query q = pm.newQuery("select key from " + clazz.getSimpleName());
			@SuppressWarnings("unchecked")
			List<T> entities = (List<T>) q.execute();
			return entities.size();
		} finally {
			pm.close();
		}
	}

	public T get(long id) throws ServiceException {
		return get(KeyFactory.createKey(clazz.getSimpleName(), id));
	}

	public T get(String name) throws ServiceException {
		return get(KeyFactory.createKey(clazz.getSimpleName(), name));
	}

	public T get(Key key) throws ServiceException {
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			return pm.getObjectById(clazz, key);
		} catch(JDOObjectNotFoundException e) {
			LOG.log(Level.INFO, "The entity was not found (possibly just deleted) using key " + key);
			return null;
		} finally {
			pm.close();
		}
	}

	public List<T> getAll() throws ServiceException {
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			Query q = pm.newQuery(clazz);
			@SuppressWarnings("unchecked")
			List<T> entities = (List<T>) q.execute();
			return entities;
		} finally {
			pm.close();
		}
	}

	public void put(T entity) throws ServiceException {
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			pm.makePersistent(entity);
		} finally {
			pm.close();
		}
	}

	public void delete(T entity) throws ServiceException {
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			pm.deletePersistent(pm.getObjectById(clazz, entity.getKey()));
		} catch(JDOObjectNotFoundException e) {
			LOG.log(Level.INFO, "The entity was already deleted with key " + entity.getKey());
			return;
		} finally {
			pm.close();
		}
	}

	protected String getUsersId() throws NotLoggedInException {
		UserService userService = UserServiceFactory.getUserService();

		if (userService.isUserLoggedIn()) {
			User user = userService.getCurrentUser();
			return user.getUserId();
		} else {
			LOG.log(Level.SEVERE, "There is no user currently logged in.");
			throw new NotLoggedInException();
		}
	}

	protected Key getUsersKey() throws NotLoggedInException {
		return KeyFactory.createKey(RegisteredUser.class.getSimpleName(), getUsersId());
	}

}
