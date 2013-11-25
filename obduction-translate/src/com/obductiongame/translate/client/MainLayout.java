package com.obductiongame.translate.client;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.ScrollPanel;

public interface MainLayout {

	void setPresenter(Presenter presenter);

	ScrollPanel getActivityPanel();

	void setUserName(String userName);
	void setLoginLink(String url);
	void setLogoutLink(String url);

	public interface Presenter {

		void goTo(Place place);

	}

}
