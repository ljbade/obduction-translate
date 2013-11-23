package com.obductiongame.translate.client.view;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class EditPlace extends Place {

	private String token;

	public EditPlace(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	@Prefix("edit")
	public static class Tokenizer implements PlaceTokenizer<EditPlace> {
		@Override
		public EditPlace getPlace(String token) {
			return new EditPlace(token);
		}

		@Override
		public String getToken(EditPlace place) {
			return place.getToken();
		}
	}

}
