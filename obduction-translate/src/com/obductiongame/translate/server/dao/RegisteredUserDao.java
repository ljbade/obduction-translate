package com.obductiongame.translate.server.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import com.obductiongame.translate.server.NotLoggedInException;
import com.obductiongame.translate.server.ServiceException;
import com.obductiongame.translate.server.ValidationException;
import com.obductiongame.translate.server.WrongUserException;
import com.obductiongame.translate.server.entity.RegisteredUser;

public class RegisteredUserDao extends EntityDao<RegisteredUser> {

	private static final Logger LOG = Logger.getLogger(RegisteredUserDao.class.getName());

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
		return get(getUsersKey());
	}

	@Override
	public List<RegisteredUser> getAll() throws ServiceException {
		throw new UnsupportedOperationException("Retrieving all users is not allowed.");
	}

	@Override
	public void put(RegisteredUser user) throws ValidationException, NotLoggedInException, WrongUserException {
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			if (user.getKey() == null) {
				user.setKey(getUsersKey());
			} else {
				if (!user.getKey().equals(getUsersKey())) {
					LOG.log(Level.SEVERE, "The entity's owner is not the user currently logged in with key " + user.getKey());
					throw new WrongUserException();
				}
			}
			user.setId(getUsersId());
			pm.makePersistent(user);
		} finally {
			pm.close();
		}
	}

}
