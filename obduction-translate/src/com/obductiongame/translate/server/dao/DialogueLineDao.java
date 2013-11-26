package com.obductiongame.translate.server.dao;

//import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import com.obductiongame.translate.server.NotLoggedInException;
import com.obductiongame.translate.server.ServiceException;
import com.obductiongame.translate.server.ValidationException;
import com.obductiongame.translate.server.WrongUserException;
import com.obductiongame.translate.server.entity.DialogueLine;

public class DialogueLineDao extends UserRequiredDao<DialogueLine> {

	//private static final Logger LOG = Logger.getLogger(DialogueLineDao.class.getName());

	public DialogueLineDao() {
		super(DialogueLine.class);
	}

	@Override
	public void put(DialogueLine line) throws ValidationException, WrongUserException, NotLoggedInException {
		/*if (Arrays.binarySearch(LanguageLoader.getLanguages(), new LanguageImpl(line.getLanguage())) < 0) {
			LOG.log(Level.SEVERE, "The dialogue line has an invalid language code.");
			throw new ValidationException("The dialogue line has an invalid language code.");
		}*/ // TODO: figure out why this does not always work

		PersistenceManager pm = PMF.getPersistenceManager();
		try {//TODO handle overwriting in the UI
			// TODO: add key name/id as entity parameters, make it easier to set name? both here and for user + line entities
			requireOrSetUserAsParent(line, DialogueLine.class, line.toName());
			pm.makePersistent(line);
		} finally {
			pm.close();
		}
	}

	// Needed to work around bug (https://code.google.com/p/google-web-toolkit/issues/detail?id=6794)
	@Override
	public void delete(DialogueLine entity) throws ServiceException {
		super.delete(entity);
	}

}
