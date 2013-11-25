package com.obductiongame.translate.server;

import javax.servlet.ServletException;

import com.google.gwt.logging.server.RemoteLoggingServiceImpl;

@SuppressWarnings("serial")
public class CustomRemoteLoggingService extends RemoteLoggingServiceImpl {

	@Override
	public void init() throws ServletException {
		super.init();
		String symbolMapsDirectory = getInitParameter("symbolMapsDirectory");
		if (symbolMapsDirectory != null) {
			setSymbolMapsDirectory(symbolMapsDirectory);
		}
	}

}
