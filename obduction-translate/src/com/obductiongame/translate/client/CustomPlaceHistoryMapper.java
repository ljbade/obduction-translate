package com.obductiongame.translate.client;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;

public class CustomPlaceHistoryMapper implements MainPlaceHistoryMapper {

	private PlaceHistoryMapper historyMapper;

	public CustomPlaceHistoryMapper(PlaceHistoryMapper historyMapper) {
		this.historyMapper = historyMapper;
	}

	@Override
	public Place getPlace(String token) {
		if (token.startsWith("!")) {
			token = token.substring(1);
		}
		return historyMapper.getPlace(token);
	}

	@Override
	public String getToken(Place place) {
		return "!" + historyMapper.getToken(place);
	}

}
