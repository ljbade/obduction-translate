package com.obductiongame.translate.server.dao;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import com.obductiongame.translate.server.LanguageLoader;
import com.obductiongame.translate.server.ValidationException;
import com.obductiongame.translate.server.entity.DialogueLine;
import com.obductiongame.translate.shared.LanguageImpl;

public class DialogueLineDaoImpl implements DialogueLineDao {

	private static final Logger LOG = Logger.getLogger(DialogueLineDaoImpl.class.getName());
	private static final PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");

	@Override
	public long count() {
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			Query q = pm.newQuery("select key from " + DialogueLine.class.getName());
			@SuppressWarnings("unchecked")
			List<DialogueLine> lines = (List<DialogueLine>) q.execute();
			return lines.size();
		} finally {
			pm.close();
		}
	}

	@Override
	public DialogueLine get(String key) {
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			return pm.getObjectById(DialogueLine.class, key);
		} catch(JDOObjectNotFoundException e) {
			return null;
		} finally {
			pm.close();
		}
	}

	@Override
	public List<DialogueLine> getAll() { // TODO: we will eventually need to paginate
		PersistenceManager pm = PMF.getPersistenceManager();
		try {

			Query q = pm.newQuery(DialogueLine.class);
			q.setOrdering("id asc, language asc");

			@SuppressWarnings("unchecked")
			List<DialogueLine> lines = (List<DialogueLine>) q.execute();
			return lines;
		} finally {
			pm.close();
		}
	}

	@Override
	public void put(DialogueLine line) throws ValidationException {
		/*if (Arrays.binarySearch(LanguageLoader.getLanguages(), new LanguageImpl(line.getLanguage())) < 0) {
			LOG.log(Level.SEVERE, "The dialogue line has an invalid language code.");
			throw new ValidationException("The dialogue line has an invalid language code.");
		}*/ // TODO: figure out why this does not always work

		PersistenceManager pm = PMF.getPersistenceManager(); // TODO: cover to transaction as per google jdo pages
		try {//https://groups.google.com/forum/#!topic/objectify-appengine/Jamr_Ib4vlE
			Query q = pm.newQuery("select key from " + DialogueLine.class.getName());
			q.setFilter("(id == line.id) && (language == line.language)");
			q.declareParameters(DialogueLine.class.getName() + " line");
			q.setRange(0, 1);

			@SuppressWarnings("unchecked")
			List<DialogueLine> lines = (List<DialogueLine>) q.execute(line);

			if (!lines.isEmpty()) {//TODO: test once we put everything in a single entity group
				LOG.log(Level.SEVERE, "A dialogue line with the same ID and language already exists.");
				throw new ValidationException("A dialogue line with the same ID and language already exists.");
			}// FIXME

			// TODO:each line should be in a entity group with the user/creator,
			// hard set in game lines can use a admin/cyan user
			pm.makePersistent(line);
		} finally {
			pm.close();
		}
	}


	@Override
	public void delete(DialogueLine line) {
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			pm.deletePersistent(pm.getObjectById(DialogueLine.class, line.getKey()));
		} catch(JDOObjectNotFoundException e) {
			return;
		} finally {
			pm.close();
		}
	}

}
