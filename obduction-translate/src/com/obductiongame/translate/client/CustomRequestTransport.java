package com.obductiongame.translate.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.web.bindery.requestfactory.gwt.client.DefaultRequestTransport;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public class CustomRequestTransport extends DefaultRequestTransport {

	public static final String SERVER_ERROR = "Server Error";
	public static final String TRANSPORT_ERROR = "Transport Error";
	private static final Logger wireLogger = Logger.getLogger("WireActivityLogger");

	@Override
	protected RequestCallback createRequestCallback(final TransportReceiver receiver) {
		return new RequestCallback() {
			public void onError(Request request, Throwable exception) {
				wireLogger.log(Level.SEVERE, TRANSPORT_ERROR, exception);
				receiver.onTransportFailure(new ServerFailure(exception.getMessage(), exception.getClass().getName(), TRANSPORT_ERROR, true));
			}

			public void onResponseReceived(Request request, Response response) {
				wireLogger.finest("Response received");
				if (Response.SC_OK == response.getStatusCode()) {
					String text = response.getText();
					receiver.onTransportSuccess(text);
				} else {
					String message = SERVER_ERROR + " " + response.getStatusCode() + " " + response.getText();
					wireLogger.severe(message);
					receiver.onTransportFailure(new ServerFailure(message, SERVER_ERROR, Integer.toString(response.getStatusCode()), true));
				}
			}
		};
	}

}
