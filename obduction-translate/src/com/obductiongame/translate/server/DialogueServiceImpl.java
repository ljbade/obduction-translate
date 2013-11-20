package com.obductiongame.translate.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.obductiongame.translate.client.DialogueService;
import com.obductiongame.translate.shared.DialogueLine;

@SuppressWarnings("serial")
public class DialogueServiceImpl extends RemoteServiceServlet implements
		DialogueService {

	private static final Logger LOG = Logger.getLogger(DialogueServiceImpl.class.getName());
	private static final PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");

	@Override
	public DialogueLine[] getLines() {
		LOG.log(Level.INFO, "getLines(): called");

		PersistenceManager pm = PMF.getPersistenceManager();
		List<DialogueLine> lines = new ArrayList<DialogueLine>();

		try { // TODO: exception?
			Extent<DialogueLine> e = pm.getExtent(DialogueLine.class, false);

			for (DialogueLine line : e) {
				lines.add(line);
			}
		} finally {
			pm.close();
		}

		return (DialogueLine[]) lines.toArray(new DialogueLine[0]);
	}

	@Override
	public void addLine(DialogueLine line) throws Exception {
		LOG.log(Level.INFO, "addLine(): called");

		PersistenceManager pm = PMF.getPersistenceManager();
		try { // TODO: exception?
			pm.makePersistent(line);
		} finally {
			pm.close();
		}
	}

	@Override
	public void deleteLine(Long id) throws Exception {
		LOG.log(Level.INFO, "deleteLine(): called");

		PersistenceManager pm = PMF.getPersistenceManager();
		try { // TODO: exception? what if already deleted?
			pm.deletePersistent(pm.getObjectById(DialogueLine.class, id));
		} finally {
			pm.close();
		}
	}

}
