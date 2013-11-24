package com.obductiongame.translate.server;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.utils.SystemProperty;
import com.google.web.bindery.requestfactory.server.ExceptionHandler;
import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

@SuppressWarnings("serial")
public class CustomRequestFactoryServlet extends RequestFactoryServlet {

	static class LoggingExceptionHandler implements ExceptionHandler {
		private static final Logger LOG = Logger.getLogger(LoggingExceptionHandler.class.getName());

		@Override
		public ServerFailure createServerFailure(Throwable throwable) {
			LOG.log(Level.SEVERE, "Exception during request handler", throwable);
			boolean isDevelopment = SystemProperty.environment.value() == SystemProperty.Environment.Value.Development;
			String stackTrace = null;
			if (isDevelopment) {
				StringWriter sw = new StringWriter();
				throwable.printStackTrace(new PrintWriter(sw));
				stackTrace = sw.toString();
			}
			return new ServerFailure(throwable.getMessage(), throwable.getClass().getName(), stackTrace, true);
		}
	}

	public CustomRequestFactoryServlet() {
		super( new LoggingExceptionHandler() );
	}

}
