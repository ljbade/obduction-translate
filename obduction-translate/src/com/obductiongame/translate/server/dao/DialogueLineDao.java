package com.obductiongame.translate.server.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.obductiongame.translate.server.ValidationException;
import com.obductiongame.translate.server.entity.DialogueLine;

public class DialogueLineDao extends Dao<DialogueLine> {

	private static final Logger LOG = Logger.getLogger(DialogueLineDao.class.getName());
// TODO: use id + lang as key to find entity faster
	public DialogueLineDao() {
		super(DialogueLine.class);
	}

	@Override
	public void put(DialogueLine line) throws ValidationException {
		/*if (Arrays.binarySearch(LanguageLoader.getLanguages(), new LanguageImpl(line.getLanguage())) < 0) {
			LOG.log(Level.SEVERE, "The dialogue line has an invalid language code.");
			throw new ValidationException("The dialogue line has an invalid language code.");
		}*/ // TODO: figure out why this does not always work

		PersistenceManager pm = PMF.getPersistenceManager(); // TODO: cover to transaction as per google jdo pages
		try {//https://groups.google.com/forum/#!topic/objectify-appengine/Jamr_Ib4vlE
			Query q = pm.newQuery("select key from " + DialogueLine.class.getName());// TODO: replace with a get key in tx
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
			if (line.getKey() == null) {
				line.setKey(line.toKey());
			}
			pm.makePersistent(line);
		} finally {
			pm.close();
		}
	}

}
