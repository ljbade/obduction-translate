package com.obductiongame.translate.server.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.obductiongame.translate.server.ValidationException;
import com.obductiongame.translate.server.entity.RegisteredUser;

public class RegisteredUserDao {

	private static final Logger LOG = Logger.getLogger(RegisteredUserDao.class.getName());
	private static final PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");

	public long count() {
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			Query q = pm.newQuery("select key from " + RegisteredUser.class.getName());
			@SuppressWarnings("unchecked")
			List<RegisteredUser> lines = (List<RegisteredUser>) q.execute();
			return lines.size();
		} finally {
			pm.close();
		}
	}

	public RegisteredUser get() {
		// TODO
		return get(null);
	}

	public RegisteredUser get(String key) {
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			return pm.getObjectById(RegisteredUser.class, key);
		} catch(JDOObjectNotFoundException e) {
			return null;
		} finally {
			pm.close();
		}
	}

	public List<RegisteredUser> getAll() {
		PersistenceManager pm = PMF.getPersistenceManager();
		try {

			Query q = pm.newQuery(RegisteredUser.class);

			@SuppressWarnings("unchecked")
			List<RegisteredUser> lines = (List<RegisteredUser>) q.execute();
			return lines;
		} finally {
			pm.close();
		}
	}

	public void put(RegisteredUser user) throws ValidationException {
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			pm.makePersistent(user);
		} finally {
			pm.close();
		}
	}


	public void delete(RegisteredUser user) {
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			pm.deletePersistent(pm.getObjectById(RegisteredUser.class, user.getKey()));
		} catch(JDOObjectNotFoundException e) {
			return;
		} finally {
			pm.close();
		}
	}

}
