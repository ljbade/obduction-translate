package com.obductiongame.translate.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.obductiongame.translate.client.DialogueService;
import com.obductiongame.translate.shared.DialogueLine;
import com.obductiongame.translate.shared.Language;

@SuppressWarnings("serial")
public class DialogueServiceImpl extends RemoteServiceServlet implements
		DialogueService {

	private static final Logger LOG = Logger.getLogger(DialogueServiceImpl.class.getName());
	private static final PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");

	private Language[] languagesArray;

	@Override
	public DialogueLine[] getLines() {
		LOG.log(Level.INFO, "getLines(): called");

		PersistenceManager pm = PMF.getPersistenceManager();
		ArrayList<DialogueLine> lines = new ArrayList<DialogueLine>();

		try { // TODO: exception?
			Extent<DialogueLine> e = pm.getExtent(DialogueLine.class, false); // TODO: may be more efficient to use query, and sort in db

			for (DialogueLine line : e) {
				lines.add(line);
			}
		} finally {
			pm.close();
		}

		DialogueLine[] linesArray = lines.toArray(new DialogueLine[0]);
		Arrays.sort(linesArray);
		return linesArray;
	}

	@Override
	public String addLine(DialogueLine line) throws Exception { // TODO: check for valid language
		LOG.log(Level.INFO, "addLine(): called");// TODO: need to relax? only check on id and language, also need to repeat this on the server

		if (Arrays.binarySearch(getLanguages(), new Language(line.getLanguage())) < 0) {//TODO: test bad lang afer fixed
			//FIXME:always fails
			LOG.log(Level.INFO, "addLine(): The dialogue line has an invalid language code.");
			//throw new Exception("The dialogue line has an invalid language code.");
		}

		String key;
		PersistenceManager pm = PMF.getPersistenceManager(); // TODO: cover to transaction as per google jdo pages
		try { // TODO: exception?
			Query q = pm.newQuery(DialogueLine.class);
			q.setFilter("(id == line.id) && (language == line.language)");
			q.declareParameters(DialogueLine.class.getName() + " line");
			q.setRange(0, 0);
			@SuppressWarnings("unchecked")
			List<DialogueLine> lines = (List<DialogueLine>) q.execute(line);
			if (!lines.isEmpty()) {//TODO: test once we put everything in a single entity group
				LOG.log(Level.INFO, "addLine(): A dialogue line with the same ID and language already exists.");
				throw new Exception("A dialogue line with the same ID and language already exists.");
			}
			key = pm.makePersistent(line).getKey();// TODO:each line should be in a entity group with the user/creator,
			// hard set in game lines can use a admin/cyan user
		} finally {
			pm.close();
		}
		return key;
	}

	@Override
	public void deleteLine(String key) throws Exception {//TODO: dont need exception?
		LOG.log(Level.INFO, "deleteLine(): called");

		PersistenceManager pm = PMF.getPersistenceManager();
		try { // TODO: exception? what if already deleted? should log too
			//TODO: make in transaction
			pm.deletePersistent(pm.getObjectById(DialogueLine.class, key));
		} finally {
			pm.close();
		}
	}

	@Override
	public Language[] getLanguages() {// TODO: Should cache this data
		// TODO: languages should probably be store in the db
		LOG.log(Level.INFO, "getLanguages(): called");

		if (languagesArray == null) {
			LOG.log(Level.INFO, "getLanguages(): languagesArray is null, creating");
			ArrayList<Language> languages = new ArrayList<Language>();
			
			String[] languageCodes = Locale.getISOLanguages();
			for (String languageCode : languageCodes) {
				String languageName = new Locale(languageCode).getDisplayLanguage(Locale.ENGLISH);
				languages.add(new Language(languageCode, languageName));
			}
	
			languagesArray = languages.toArray(new Language[0]);
			Arrays.sort(languagesArray, new Language.NameComparator());
		}

		return languagesArray;
	}

}
