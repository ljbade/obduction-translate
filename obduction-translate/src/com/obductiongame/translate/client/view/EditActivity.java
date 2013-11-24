package com.obductiongame.translate.client.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.obductiongame.translate.client.ClientFactory;
import com.obductiongame.translate.client.ErrorHandler;
import com.obductiongame.translate.client.ErrorHandler.ErrorReceiver;
import com.obductiongame.translate.client.jso.JsArray;
import com.obductiongame.translate.client.jso.LanguageJso;
import com.obductiongame.translate.client.proxy.DialogueLineProxy;
import com.obductiongame.translate.client.proxy.DialogueLineComparator;
import com.obductiongame.translate.client.request.DialogueLineRequest;
import com.obductiongame.translate.client.view.EditView.Presenter;
import com.obductiongame.translate.shared.Language;

public class EditActivity extends AbstractActivity implements Presenter {

	private static final Logger LOG = Logger.getLogger(EditActivity.class.getName());

	private ClientFactory clientFactory;
	private EditView view;

	private final ArrayList<DialogueLineProxy> lineList = new ArrayList<DialogueLineProxy>();
	private final TreeMap<String, String> languageMap = new TreeMap<String, String>();
	
	private boolean editing = false;

	public EditActivity(EditPlace place, ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
		clientFactory.getDialogueService();
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
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
		if (editing) { // TODO: need to test
			return "If you leave this page you will lose and unsaved edits. Do you want to continue?";
		}
		return null;
	}

	@Override
	public void addDialogue(String idText, String dialogue, String language) {
		editing = false;

		// Check ID is a integer
		int id;
		try {
			id = Integer.parseInt(idText);
		} catch (NumberFormatException e) {
			ErrorHandler.showError("'" + idText + "' is not a valid ID.");
			view.selectDialogueId();
			return;
		}

		DialogueLineRequest req = clientFactory.getDialogueLineRequest();
		final DialogueLineProxy line = req.create(DialogueLineProxy.class);
		line.setId(id);
		line.setDialogue(dialogue);
		line.setLanguage(language);

		// Check an identical dialogue line is not already present
		if (lineList.contains(line)) {// FIXME
			ErrorHandler.showError("A dialogue line with the same ID and language already exists.");
			view.selectDialogueId();
			return;
		}

		// Add the dialogue line
		req.put(line).fire(new ErrorReceiver<Void>(EditActivity.class.getName(), "Adding dialogue line") {
			@Override
			public void onSuccess(Void response) {
				int index = Collections.binarySearch(lineList, line, new DialogueLineComparator());
				index = index < 0 ? -(index + 1) : index;

				lineList.add(index, line);
				view.insertDialogue(line, index);
				view.setDialogueLanguage(languageMap.get(line.getLanguage().toLowerCase()), index);

				// Clear the text boxes and reset focus
				view.clearDialogue();
			}
		});
	}

	@Override
	public void deleteDialogue(DialogueLineProxy line) {
		final int index = lineList.indexOf(line);
		if (index == -1) {
			LOG.log(Level.WARNING, "Dialogue line " + line.toString() + " not found");
			return;
		}
		DialogueLineRequest req = clientFactory.getDialogueLineRequest();
		req.delete(line).fire(new ErrorReceiver<Void>(EditActivity.class.getName(), "Deleting dialogue line") {
			@Override
			public void onSuccess(Void response) {
				lineList.remove(index);
				view.deleteDialogue(index);
			}
		});
	}

	@Override
	public void editDialogue(DialogueLineProxy line) {
		editing = true;
		deleteDialogue(line);
		view.editDialogue(line);
	}

	@Override
	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}

	private void loadDialogue() {
		DialogueLineRequest req = clientFactory.getDialogueLineRequest();
		req.getAll().fire(new ErrorReceiver<List<DialogueLineProxy>>(EditActivity.class.getName(), "Loading dialogue lines") {
			@Override
			public void onSuccess(List<DialogueLineProxy> response) {
				int index = 0;
				for (DialogueLineProxy line : response) {
					lineList.add(line);
					view.addDialogue(line);
					view.setDialogueLanguage(languageMap.get(line.getLanguage().toLowerCase()), index);
					index++;
				}
				
			}
		});
	}

	private void loadLanguages() {//TODO: too slow to load via js, need to do json request after ui loaded
		JsArray<LanguageJso> languages = LanguageJso.getLanguages();
		for (int i = 0; i < languages.length(); i++) {
			Language language = languages.get(i);
			view.addLanguage(language);
			languageMap.put(language.getCode().toLowerCase(), language.getName());
		}

		for (int i = 0; i < lineList.size(); i++) {
			view.setDialogueLanguage(languageMap.get(lineList.get(i).getLanguage().toLowerCase()), i);
		}
	}

	@Override
	public void clearDialogue() {
		editing = false;
	}

}
