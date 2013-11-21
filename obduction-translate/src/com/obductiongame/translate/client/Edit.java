package com.obductiongame.translate.client;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.obductiongame.translate.shared.DialogueLine;
import com.obductiongame.translate.shared.Language;

public class Edit implements EntryPoint {

	private static final Logger LOG = Logger.getLogger(Edit.class.getName());

	private final FlexTable dialogueFlexTable = new FlexTable();
	private final VerticalPanel mainPanel = new VerticalPanel();

	private final TextBox newDialogueIDTextBox = new TextBox();
	private final TextBox newDialogueTextBox = new TextBox();
	private final ListBox newDialogueLanguageListBox = new ListBox();
	private final Button addDialogueButton = new Button("Add");
	private final Button cancelDialogueButton = new Button("Cancel");
	private final HorizontalPanel addPanel = new HorizontalPanel();

	private final ArrayList<DialogueLine> lineList = new ArrayList<DialogueLine>();
	private final TreeMap<String, String> languageMap = new TreeMap<String, String>();

	private DialogueServiceAsync dialogueService = GWT.create(DialogueService.class);

	public void onModuleLoad() {
		LOG.log(Level.INFO, "onModuleLoad(): called");

		// Create the table of dialogue lines
		// TODO: need to handle unsync between client and server
		// TODO: need to make consistent group?
		// TODO: need to handle gap between user submit data and it shows up
		// TODO: update when new data is posted?
		dialogueFlexTable.setText(0, 0, "ID");
		dialogueFlexTable.setText(0, 1, "Dialogue");
		dialogueFlexTable.setText(0, 2, "Language");
		dialogueFlexTable.setText(0, 3, "Edit");
		dialogueFlexTable.setText(0, 4, "Delete");
		mainPanel.add(dialogueFlexTable);
		RootPanel.get("content").add(mainPanel);

		// Create the add new dialogue panel
		addPanel.add(newDialogueIDTextBox);
		addPanel.add(newDialogueTextBox);
		addPanel.add(newDialogueLanguageListBox);
		addPanel.add(addDialogueButton);
		addPanel.add(cancelDialogueButton);
		RootPanel.get("content").add(addPanel);

		// Set the focus to the text box
		newDialogueIDTextBox.setFocus(true);

		// Listen for a click on the add button
		addDialogueButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				LOG.log(Level.INFO, "addDialogButton.onClick(): called with " + event.toDebugString());
				addDialogue();
			}
		});

		// Listen for enter in the input box.
		newDialogueTextBox.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				LOG.log(Level.INFO, "addDialogButton.onKeyDown(): called with " + event.toDebugString());
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					addDialogue();
				}
			}
		});

		// Listen for a click on the cancel button
		cancelDialogueButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				LOG.log(Level.INFO, "cancelDialogueButton.onClick(): called with " + event.toDebugString());
				newDialogueIDTextBox.setText("");
				newDialogueTextBox.setText("");
				newDialogueLanguageListBox.setSelectedIndex(0);
				newDialogueIDTextBox.setFocus(true);
			}
		});

		// Load the languages
		loadLanguages();

		// Load existing dialogue
		loadDialogue();
	}

	private void addDialogue() {
		LOG.log(Level.INFO, "addDialogue(): called");

		// Check ID is a integer
		int id;
		try {
			id = Integer.parseInt(newDialogueIDTextBox.getText());
		} catch (NumberFormatException e) {
			Window.alert("'" + newDialogueIDTextBox.getText() + "' is not a valid ID.");
			LOG.log(Level.INFO, "addDialogue(): '" + newDialogueIDTextBox.getText() + "' is not a valid ID.");
			newDialogueIDTextBox.selectAll();
			newDialogueIDTextBox.setFocus(true);
			return;
		}

		final DialogueLine line = new DialogueLine(id, newDialogueTextBox.getText(), newDialogueLanguageListBox.getValue(newDialogueLanguageListBox.getSelectedIndex()));
		LOG.log(Level.INFO, "addDialogue(): line = " + line.toString());

		// Check an identical dialogue line is not already present
		if (lineList.contains(line)) {
			Window.alert("A dialogue line with the same ID and language already exists.");
			LOG.log(Level.INFO, "addDialogue(): A dialogue line with the same ID and language already exists.");
			newDialogueIDTextBox.selectAll();
			newDialogueIDTextBox.setFocus(true);
			return;
		}

		// Clear the text boxes and reset focus
		newDialogueIDTextBox.setText("");
		newDialogueTextBox.setText("");
		newDialogueLanguageListBox.setSelectedIndex(0);
		newDialogueIDTextBox.setFocus(true);

		// Initialise the service proxy
		if (dialogueService == null) {
			dialogueService = GWT.create(DialogueService.class);
		}

		// Add the dialogue line
		LOG.log(Level.INFO, "addDialogue(): calling addLine()");
		dialogueService.addLine(line, new AsyncCallback<String>() {
			public void onSuccess(String result) {
				LOG.log(Level.INFO, "addLine(): success");
				line.setKey(result);
				insertTableRow(line);
			}

			@Override
			public void onFailure(Throwable caught) {
				LOG.log(Level.SEVERE, "addLine(): failure: " + caught.getMessage());
				Window.alert("Error: " + caught.getMessage());
			}
		});
	}
	
	private void addTableRow(DialogueLine line) {
		LOG.log(Level.INFO, "addTableRow(): called with " + line.toString());
		createTableRow(line, false);
	}
	
	private void insertTableRow(DialogueLine line) {
		LOG.log(Level.INFO, "insertTableRow(): called with " + line.toString());
		createTableRow(line, true);
	}

	private void createTableRow(final DialogueLine line, boolean sort) {
		LOG.log(Level.INFO, "createTableRow(): called with " + line.toString() + ", " + Boolean.toString(sort));

		int row = dialogueFlexTable.getRowCount();
		if (sort) {
			for (row = 1; row < dialogueFlexTable.getRowCount(); row++) {
				if (lineList.get(row - 1).compareTo(line) >= 0) {
					break;
				}
			}
		}

		lineList.add(row - 1, line);
		//if (row != dialogueFlexTable.getRowCount()) {
			dialogueFlexTable.insertRow(row);
		//}
		dialogueFlexTable.setText(row, 0, Integer.toString(line.getId()));
		dialogueFlexTable.setText(row, 1, line.getDialogue());
		dialogueFlexTable.setText(row, 2, languageMap.get(line.getLanguage().toLowerCase()));

		// Add the edit button
		// TODO: make work in place and not delete before add
		Button editDialogueButton = new Button("Edit");
		editDialogueButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				LOG.log(Level.INFO, "editDialogueButton.onClick(): called with " + event.toDebugString());
				editDialogue(line);
			}
		});
		dialogueFlexTable.setWidget(row, 3, editDialogueButton);

		// Add the delete button
		Button deleteDialogueButton = new Button("Delete");
		deleteDialogueButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				LOG.log(Level.INFO, "deleteDialogueButton.onClick(): called with " + event.toDebugString());
				deleteDialogue(line);
			}
		});
		dialogueFlexTable.setWidget(row, 4, deleteDialogueButton);
	}

	private void deleteDialogue(final DialogueLine line) {
		LOG.log(Level.INFO, "deleteDialogue(): called with " + line.toString());

		// Initialise the service proxy
		if (dialogueService == null) {
			dialogueService = GWT.create(DialogueService.class);
		}

		dialogueService.deleteLine(line.getKey(), new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				LOG.log(Level.INFO, "deleteLine(): success");
				int deletedIndex = lineList.indexOf(line);
				if (deletedIndex == -1) {
					LOG.log(Level.WARNING, "deleteLine(): line " + line.toString() + " not found");
					return;
				}
				lineList.remove(deletedIndex);
				dialogueFlexTable.removeRow(deletedIndex + 1);
			}

			@Override
			public void onFailure(Throwable caught) {
				LOG.log(Level.SEVERE, "deleteLine(): failure: " + caught.toString());//TODO:exceptions
				Window.alert("Error: " + caught.toString());
			}
		});
	}

	private void editDialogue(final DialogueLine line) {
		LOG.log(Level.INFO, "editDialogue(): called with " + line.toString());

		deleteDialogue(line);
		newDialogueIDTextBox.setText(Integer.toString(line.getId()));
		newDialogueTextBox.setText(line.getDialogue());
		newDialogueLanguageListBox.setSelectedIndex(0);
		for (int i = 0; i < newDialogueLanguageListBox.getItemCount(); i++) {
			boolean found = false;
			if (line.getLanguage().equals(newDialogueLanguageListBox.getValue(i))) {
				newDialogueLanguageListBox.setSelectedIndex(i);
				found = true;
			}
			if (!found) {
				newDialogueLanguageListBox.setSelectedIndex(0);
				LOG.log(Level.WARNING, "editDialogue(): language '" + line.getLanguage() + "' not found");
			}
		}
		newDialogueTextBox.selectAll();
		newDialogueTextBox.setFocus(true);
	}

	private void loadDialogue() {
		LOG.log(Level.INFO, "editDialogue(): called");

		// Initialise the service proxy
		if (dialogueService == null) {
			dialogueService = GWT.create(DialogueService.class);
		}

		dialogueService.getLines(new AsyncCallback<DialogueLine[]>() {
			@Override
			public void onSuccess(DialogueLine[] result) {
				LOG.log(Level.INFO, "getLines(): success");
				if (result != null) {
					for (DialogueLine line : result) {
						addTableRow(line);
					}
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

		// Initialise the service proxy
		if (dialogueService == null) {
			dialogueService = GWT.create(DialogueService.class);
		}

		dialogueService.getLanguages(new AsyncCallback<Language[]>() {
			@Override
			public void onSuccess(Language[] result) {
				LOG.log(Level.INFO, "getLanguages(): success");
				if (result != null) {
					for (Language language : result) {
						newDialogueLanguageListBox.addItem(language.getName(), language.getCode());
						languageMap.put(language.getCode().toLowerCase(), language.getName());
					}
				}
			}

			@Override
			public void onFailure(Throwable caught) { // TODO: need better error handling of random errors like the "0" error for network problem
				LOG.log(Level.SEVERE, "getLanguages(): failure: " + caught.toString());
				Window.alert("Error: " + caught.toString());
			}
		});
	}

}
