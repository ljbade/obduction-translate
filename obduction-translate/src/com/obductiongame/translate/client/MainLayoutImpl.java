package com.obductiongame.translate.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

public class MainLayoutImpl implements MainLayout {

	private static LayoutViewImplUiBinder uiBinder = GWT.create(LayoutViewImplUiBinder.class);

	protected interface LayoutViewImplUiBinder extends UiBinder<DockLayoutPanel, MainLayoutImpl> {
	}

	private Presenter presenter;

	@UiField
	protected ScrollPanel activityPanel;

	@UiField
	protected SpanElement userNameSpan;
	@UiField
	protected AnchorElement loginLink;
	@UiField
	protected AnchorElement logoutLink;

	public MainLayoutImpl() {
		super();
		RootLayoutPanel.get().add(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public ScrollPanel getActivityPanel() {
		return activityPanel;
	}

	@Override
	public void setUserName(String userName) {
		userNameSpan.setInnerText(userName);
	}

	@Override
	public void setLoginLink(String url) {
		loginLink.setHref(url);
	}

	@Override
	public void setLogoutLink(String url) {
		logoutLink.setHref(url);
	}

}
