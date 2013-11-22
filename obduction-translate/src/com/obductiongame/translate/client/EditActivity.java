package com.obductiongame.translate.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.obductiongame.translate.client.EditView.Presenter;
import com.obductiongame.translate.shared.DialogueLine;
import com.obductiongame.translate.shared.Language;

public class EditActivity extends AbstractActivity implements Presenter {

	private static final Logger LOG = Logger.getLogger(EditActivity.class.getName());

	private ClientFactory clientFactory;
	private DialogueServiceAsync dialogueService;

	private EditView view;

	private final ArrayList<DialogueLine> lineList = new ArrayList<DialogueLine>();
	private final TreeMap<String, String> languageMap = new TreeMap<String, String>();
	
	private boolean editing = false;

	public EditActivity(EditPlace place, ClientFactory clientFactory) {
		LOG.log(Level.INFO, "EditActivity(): called with " + place.toString() + ", " + clientFactory.toString());
		this.clientFactory = clientFactory;
		dialogueService = clientFactory.getDialogueService();
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		LOG.log(Level.INFO, "start(): called with " + panel.toString() + ", " + eventBus.toString());

		view = clientFactory.getEditView();
		view.setPresenter(this);
		view.reset();
		panel.setWidget(view.asWidget());

		// Load the languages
		loadLanguages();

		// Load existing dialogue
		loadDialogue();
	}

	@Override
	public String mayStop() {
		LOG.log(Level.INFO, "mayStop(): called");
		if (editing) { // TODO: need to test
			return "If you leave this page you will lose and unsaved edits. Do you want to continue?";
		}
		return null;
	}

	@Override
	public void addDialogue(String idText, String dialogue, String language) {
		LOG.log(Level.INFO, "addDialogue(): called with " + idText + ", " + dialogue + ", " + language);

		editing = false;

		// Check ID is a integer
		int id;
		try {
			id = Integer.parseInt(idText);
		} catch (NumberFormatException e) {
			Window.alert("'" + idText + "' is not a valid ID.");
			LOG.log(Level.INFO, "addDialogue(): '" + idText + "' is not a valid ID.");
			view.selectDialogueId();
			return;
		}

		final DialogueLine line = new DialogueLine(id, dialogue, language);
		LOG.log(Level.INFO, "addDialogue(): line = " + line.toString());

		// Check an identical dialogue line is not already present
		if (lineList.contains(line)) {
			Window.alert("A dialogue line with the same ID and language already exists.");
			LOG.log(Level.INFO, "addDialogue(): A dialogue line with the same ID and language already exists.");
			view.selectDialogueId();
			return;
		}

		// Clear the text boxes and reset focus
		view.clearDialogue();//TODO:move to after onsuccess

		// Add the dialogue line
		LOG.log(Level.INFO, "addDialogue(): calling addLine()");
		dialogueService.addLine(line, new AsyncCallback<String>() {
			public void onSuccess(String result) {
				LOG.log(Level.INFO, "addLine(): success");
				line.setKey(result);

				int index = Collections.binarySearch(lineList, line);
				index = index < 0 ? -(index + 1) : index;

				lineList.add(index, line);
				view.insertDialogue(line, index);
				view.setDialogueLanguage(languageMap.get(line.getLanguage().toLowerCase()), index);
			}

			@Override
			public void onFailure(Throwable caught) {
				LOG.log(Level.SEVERE, "addLine(): failure: " + caught.getMessage());
				Window.alert("Error: " + caught.getMessage());
			}
		});
	}

	@Override
	public void deleteDialogue(final int index) {// FIXME: delete after add deletes the wrong row
		LOG.log(Level.INFO, "deleteDialogue(): called with " + Integer.toString(index));

		DialogueLine line = lineList.get(index);
		dialogueService.deleteLine(line.getKey(), new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				LOG.log(Level.INFO, "deleteLine(): success");
				lineList.remove(index);
				view.deleteDialogue(index);
			}

			@Override
			public void onFailure(Throwable caught) {
				LOG.log(Level.SEVERE, "deleteLine(): failure: " + caught.toString());//TODO:exceptions
				Window.alert("Error: " + caught.toString());
			}
		});
	}

	@Override
	public void editDialogue(int index) {
		LOG.log(Level.INFO, "editDialogue(): called with " + Integer.toString(index));

		editing = true;
		final DialogueLine line = lineList.get(index);
		deleteDialogue(index);
		view.editDialogue(line);
	}

	@Override
	public void goTo(Place place) {
		LOG.log(Level.INFO, "goTo(): called with " + place);
		clientFactory.getPlaceController().goTo(place);
	}

	private void loadDialogue() {
		LOG.log(Level.INFO, "loadDialogue(): called");

		dialogueService.getLines(new AsyncCallback<DialogueLine[]>() {
			@Override
			public void onSuccess(DialogueLine[] result) {
				LOG.log(Level.INFO, "getLines(): success");

				int index = 0;
				for (DialogueLine line : result) {
					view.addDialogue(line);
					view.setDialogueLanguage(languageMap.get(line.getLanguage().toLowerCase()), index);
					index++;
				}
			}

			@Override
			public void onFailure(Throwable caught) { // TODO: need better error handling of random errors like the "0" error for network problem
				LOG.log(Level.SEVERE, "getLines(): failure: " + caught.toString());
				Window.alert("Error: " + caught.toString());
			}
		});
	}

	private void loadLanguages() {
		LOG.log(Level.INFO, "loadLanguages(): called");

		dialogueService.getLanguages(new AsyncCallback<Language[]>() {
			@Override
			public void onSuccess(Language[] result) {
				LOG.log(Level.INFO, "getLanguages(): success");

				for (Language language : result) {
					view.addLanguage(language);
					languageMap.put(language.getCode().toLowerCase(), language.getName());
				}

				for (int i = 0; i < lineList.size(); i++) {
					view.setDialogueLanguage(languageMap.get(lineList.get(i).getLanguage().toLowerCase()), i);
				}
			}

			@Override
			public void onFailure(Throwable caught) { // TODO: need better error handling of random errors like the "0" error for network problem
				LOG.log(Level.SEVERE, "getLanguages(): failure: " + caught.toString());
				Window.alert("Error: " + caught.toString());
			}
		});
	}

	@Override
	public void clearDialogue() {
		LOG.log(Level.INFO, "clearDialogue(): called");
		editing = false;
	}

}
