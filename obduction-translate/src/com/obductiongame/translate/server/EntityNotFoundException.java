package com.obductiongame.translate.server;

@SuppressWarnings("serial")
public class EntityNotFoundException extends ServiceException {

	public EntityNotFoundException() {
		super("The entity was not found.");
	}

}
