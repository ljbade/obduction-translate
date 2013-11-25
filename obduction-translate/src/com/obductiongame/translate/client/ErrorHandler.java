package com.obductiongame.translate.client;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.google.web.bindery.event.shared.UmbrellaException;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public class ErrorHandler {

	public static class ErrorExceptionHandler implements GWT.UncaughtExceptionHandler {

		@Override
		public void onUncaughtException(Throwable e) {
			Logger log = Logger.getLogger(ErrorExceptionHandler.class.getName());
			log.log(Level.SEVERE, "Uncaught exception", unwrap(e));
			showError("An unexpected error occured.");
		}

		public Throwable unwrap(Throwable e) {
			if(e instanceof UmbrellaException) {
				UmbrellaException ue = (UmbrellaException) e;
				if(ue.getCauses().size() == 1) {
					return unwrap(ue.getCauses().iterator().next());
				}
			}
			return e;
		}
	}

	public static abstract class ErrorAsyncCallback<T> implements AsyncCallback<T> {

		private Logger log;
		private String message;

		public ErrorAsyncCallback(String name, String message) {
			super();
			log = Logger.getLogger(name);
			this.message = message;
		}

		@Override
		public void onFailure(Throwable caught) {
			log.log(Level.SEVERE, message + " failure", caught);
			if (caught instanceof StatusCodeException) {
				if (((StatusCodeException)caught).getStatusCode() == 0) {
					showError(message + ": A connection error occured during the operation.");
				} else {
					showError(message + ": A server error occured during the operation.");
				}
			} else {
				showError(message + ": An unexpected error occured during the operation.");
			}
		}

	}

	public static abstract class ErrorReceiver<T> extends Receiver<T> {

		private Logger log;
		private String message;

		public ErrorReceiver(String name, String message) {
			super();
			log = Logger.getLogger(name);
			this.message = message;
		}

		@Override
		public void onFailure(ServerFailure error) {
			String errorFormatted = "\n";
			if (error.getExceptionType() != null) {
				errorFormatted += error.getExceptionType() + ": ";
			}
			if (error.getMessage() != null) {
				errorFormatted += error.getMessage();
			}
			if (error.getStackTraceString() != null) {
				errorFormatted += "\n" + error.getStackTraceString();
			}
			log.log(Level.SEVERE, "Server failure:\n" + message + errorFormatted);

			if (error.getStackTraceString().equals(CustomRequestTransport.TRANSPORT_ERROR)) {
				showError(message + ": A connection error occured during the operation.");
			} else if (error.getExceptionType().equals(CustomRequestTransport.SERVER_ERROR)) {
				showError(message + ": A server error occured during the operation.");
			} else {
				showError(message + ": An unexpected error occured during the operation.");
			}
		}

		@Override
		public void onConstraintViolation(
				Set<ConstraintViolation<?>> violations) {
			String errorFormatted = "";
			for (ConstraintViolation<?> violation : violations) {
				errorFormatted += "Violation message: " + violation.getMessage() + "\n";
			}
			log.log(Level.SEVERE, "Validation failure:\n" + message + "\nDetails:\n" + errorFormatted);

			// TODO: more user friendly error message, must be same format and behaviour as clienside validation eg conver to int error and move focus to bad widget
			showError(message + ": The data had invalid values.");
		}

	}

	private static final Logger LOG = Logger.getLogger(ErrorHandler.class.getName());

	public static void showError(String message) {
		LOG.log(Level.WARNING, "Error: " + message);
		Window.alert("Error: " + message);
	}

}
