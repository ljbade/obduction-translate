package com.obductiongame.translate.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class MainActivityMapper implements ActivityMapper {

	private static final Logger LOG = Logger.getLogger(EditViewImpl.class.getName());

	private ClientFactory clientFactory;

	public MainActivityMapper(ClientFactory clientFactory) {
		super();

		LOG.log(Level.INFO, "MainActivityMapper(): called with " + clientFactory.toString());
		this.clientFactory = clientFactory;
	}

	@Override
	public Activity getActivity(Place place) {
		LOG.log(Level.INFO, "getActivity(): called with " + place.toString());

		if (place instanceof EditPlace)
			return new EditActivity((EditPlace) place, clientFactory);
		return null;
	}

}
