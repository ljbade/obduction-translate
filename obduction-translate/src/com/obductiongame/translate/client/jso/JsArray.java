package com.obductiongame.translate.client.jso;

import com.google.gwt.core.client.JavaScriptObject;

public class JsArray<E extends JavaScriptObject> extends JavaScriptObject {

	protected JsArray() {
		super();
	}

	public final native int length() /*-{
		return this.length;
	}-*/;

	public final native E get(int i) /*-{
		return this[i];
	}-*/;

}
