package com.obductiongame.translate.server;

@SuppressWarnings("serial")
public class WrongUserException extends ServiceException {

	public WrongUserException() {
		super("The entity's owner is not the user currently logged in.");
	}

}
