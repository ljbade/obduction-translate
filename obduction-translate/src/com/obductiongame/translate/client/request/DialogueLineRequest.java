package com.obductiongame.translate.client.request;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.obductiongame.translate.client.proxy.DialogueLineProxy;
import com.obductiongame.translate.server.dao.DialogueLineDao;
import com.obductiongame.translate.server.locator.MainServiceLocator;

@Service(value = DialogueLineDao.class, locator = MainServiceLocator.class)
public interface DialogueLineRequest extends RequestContext {

	Request<List<DialogueLineProxy>> getAll();
	Request<Void> put(DialogueLineProxy line);
	Request<Void> delete(DialogueLineProxy line);

}
