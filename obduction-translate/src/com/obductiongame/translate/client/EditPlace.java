package com.obductiongame.translate.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class EditPlace extends Place {

	private static final Logger LOG = Logger.getLogger(EditPlace.class.getName());

	private String token;

	public EditPlace(String token) {
		LOG.log(Level.INFO, "EditPlace(): called with " + token);
		this.token = token;
	}

	public String getToken() {
		LOG.log(Level.INFO, "getToken(): called");
		return token;
	}

	@Prefix("edit")
	public static class Tokenizer implements PlaceTokenizer<EditPlace> {
		@Override
		public EditPlace getPlace(String token) {
			LOG.log(Level.INFO, "getPlace(): called with " + token);
			return new EditPlace(token);
		}

		@Override
		public String getToken(EditPlace place) {
			LOG.log(Level.INFO, "getToken(): called with " + place.toString());
			return place.getToken();
		}
	}

}
