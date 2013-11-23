package com.obductiongame.translate.client.proxy;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.obductiongame.translate.server.entity.DialogueLine;
import com.obductiongame.translate.server.locator.DialogueLineLocator;

@ProxyFor(value = DialogueLine.class, locator = DialogueLineLocator.class)
public interface DialogueLineProxy extends EntityProxy {

	int getId();
	void setId(int id);
	String getDialogue();
	void setDialogue(String dialogue);
	String getLanguage();
	void setLanguage(String language);

}
