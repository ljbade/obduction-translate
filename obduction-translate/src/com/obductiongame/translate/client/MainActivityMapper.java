package com.obductiongame.translate.client;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.obductiongame.translate.client.view.EditActivity;
import com.obductiongame.translate.client.view.EditPlace;

public class MainActivityMapper implements ActivityMapper {

	private ClientFactory clientFactory;

	public MainActivityMapper(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof EditPlace)
			return new EditActivity((EditPlace) place, clientFactory);
		return null;
	}

}
