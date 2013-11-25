package com.obductiongame.translate.client.request;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
import com.obductiongame.translate.client.proxy.EntityProxy;
import com.obductiongame.translate.server.dao.EntityDao;
import com.obductiongame.translate.server.locator.MainServiceLocator;

@Service(value = EntityDao.class, locator = MainServiceLocator.class)
public interface EntityRequest<T extends EntityProxy> extends RequestContext {

	Request<Long> count();
	Request<T> get(long id);
	Request<T> get(String name);
	Request<List<T>> getAll();
	Request<Void> put(T entity);
	Request<Void> delete(T entity);

}
