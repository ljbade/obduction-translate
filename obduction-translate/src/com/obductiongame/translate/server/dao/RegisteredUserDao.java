package com.obductiongame.translate.server.dao;

import java.util.List;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.Key;
import com.obductiongame.translate.server.NotLoggedInException;
import com.obductiongame.translate.server.ServiceException;
import com.obductiongame.translate.server.ValidationException;
import com.obductiongame.translate.server.entity.RegisteredUser;

public class RegisteredUserDao extends EntityDao<RegisteredUser> {

	//private static final Logger LOG = Logger.getLogger(RegisteredUserDao.class.getName());

	public RegisteredUserDao() {
		super(RegisteredUser.class);
	}

	@Override
	public long count() throws ServiceException {
		throw new UnsupportedOperationException("Counting all the users is not allowed.");
	}

	@Override
	public void delete(RegisteredUser entity) throws ServiceException {
		throw new UnsupportedOperationException("Deleting a user is not allowed.");
	}

	public RegisteredUser get() throws ServiceException {
		Key usersKey;
		try {
			usersKey = getUsersKey();
		} catch(NotLoggedInException e) {
			return null;
		}
		return get(usersKey);
	}

	@Override
	public List<RegisteredUser> getAll() throws ServiceException {
		throw new UnsupportedOperationException("Retrieving all users is not allowed.");
	}

	@Override
	public void put(RegisteredUser user) throws ValidationException {
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			pm.makePersistent(user);
		} finally {
			pm.close();
		}
	}

}
