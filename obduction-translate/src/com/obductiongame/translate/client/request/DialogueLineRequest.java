package com.obductiongame.translate.client.request;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.Service;
import com.obductiongame.translate.client.proxy.DialogueLineProxy;
import com.obductiongame.translate.server.dao.DialogueLineDao;
import com.obductiongame.translate.server.locator.MainServiceLocator;

@Service(value = DialogueLineDao.class, locator = MainServiceLocator.class)
public interface DialogueLineRequest extends EntityRequest<DialogueLineProxy> {

	Request<Void> delete(DialogueLineProxy line); // Needed to work around bug (https://code.google.com/p/google-web-toolkit/issues/detail?id=6794)
	Request<Void> put(DialogueLineProxy line); // Needed to work around bug (https://code.google.com/p/google-web-toolkit/issues/detail?id=6794)

}
