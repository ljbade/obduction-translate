package com.obductiongame.translate.server.dao;

import javax.jdo.PersistenceManager;

import com.obductiongame.translate.server.ServiceException;
import com.obductiongame.translate.server.ValidationException;
import com.obductiongame.translate.server.entity.RegisteredUser;

public class RegisteredUserDao extends EntityDao<RegisteredUser> {

	//private static final Logger LOG = Logger.getLogger(RegisteredUserDao.class.getName());

	public RegisteredUserDao() {
		super(RegisteredUser.class);
	}

	public RegisteredUser get() throws ServiceException {
		return get(getUsersKey());
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
