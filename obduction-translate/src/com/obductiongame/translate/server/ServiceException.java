package com.obductiongame.translate.server;

@SuppressWarnings("serial")
public abstract class ServiceException extends Exception {

	public ServiceException(String message) {
		super(message);
	}

}
