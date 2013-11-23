package com.obductiongame.translate.server.locator;

import com.google.web.bindery.requestfactory.shared.Locator;
import com.obductiongame.translate.server.dao.DialogueLineDaoImpl;
import com.obductiongame.translate.server.entity.DialogueLine;

public class DialogueLineLocator extends Locator<DialogueLine, String> {

	@Override
	public DialogueLine create(Class<? extends DialogueLine> clazz) {
		return new DialogueLine();
	}

	@Override
	public DialogueLine find(Class<? extends DialogueLine> clazz, String id) {
		return new DialogueLineDaoImpl().get(id);
	}

	@Override
	public Class<DialogueLine> getDomainType() {
		return DialogueLine.class;
	}

	@Override
	public String getId(DialogueLine domainObject) {
		return domainObject.getKey();
	}

	@Override
	public Class<String> getIdType() {
		return String.class;
	}

	@Override
	public Object getVersion(DialogueLine domainObject) {
		return domainObject.getVersion();
	}

}
