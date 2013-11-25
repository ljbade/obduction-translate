package com.obductiongame.translate.server;

@SuppressWarnings("serial")
public class NotLoggedInException extends ServiceException {

	public NotLoggedInException() {
		super("There is no user currently logged in.");
	}

}
