package com.obductiongame.translate.client.jso;

import com.google.gwt.core.client.JavaScriptObject;
import com.obductiongame.translate.shared.Language;

public class LanguageJso extends JavaScriptObject implements Language {

	public static final native JsArray<LanguageJso> getLanguages() /*-{
		return $wnd.languages;
	}-*/;

	protected LanguageJso() {
	}

	@Override
	public final native String getName() /*-{
		return this.name;
	}-*/;

	@Override
	public final native void setName(String name) /*-{
		this.name = name;
	}-*/;

	@Override
	public final native String getCode() /*-{
		return this.code;
	}-*/;

	@Override
	public final native void setCode(String code) /*-{
		this.code = code;
	}-*/;

}
