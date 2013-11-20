package com.obductiongame.translate.client;

import java.util.ArrayList;
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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.obductiongame.translate.shared.DialogueLine;

public class Edit implements EntryPoint {

	private static final Logger LOG = Logger.getLogger(Edit.class.getName());

	private final FlexTable dialogueFlexTable = new FlexTable();
	private final VerticalPanel mainPanel = new VerticalPanel();

	private final TextBox newDialogueIDTextBox = new TextBox();
	private final TextBox newDialogueTextBox = new TextBox();
	private final TextBox newDialogueLanguageTextBox = new TextBox();
	private final Button addDialogueButton = new Button("Add");
	private final Button cancelDialogueButton = new Button("Cancel");
	private final HorizontalPanel addPanel = new HorizontalPanel();

	private final ArrayList<DialogueLine> lines = new ArrayList<DialogueLine>();
	private DialogueServiceAsync dialogueService = GWT.create(DialogueService.class);

	public void onModuleLoad() {
		LOG.log(Level.INFO, "onModuleLoad(): called");

		// Create the table of dialogue lines
		// TODO: keep table sorted
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
		addPanel.add(newDialogueLanguageTextBox);
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
				newDialogueLanguageTextBox.setText("");
				newDialogueIDTextBox.setFocus(true);
			}
		});
		
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

		final DialogueLine line = new DialogueLine(id, newDialogueTextBox.getText(), newDialogueLanguageTextBox.getText());
		LOG.log(Level.INFO, "addDialogue(): line = " + line.toString());

		// Check an identical dialogue line is not already present
		if (lines.contains(line)) {// TODO: need to relax? only check on lineId and languae, also need to repeat this on the server
			Window.alert("A dialogue line with the same ID and language already exists.");
			LOG.log(Level.INFO, "addDialogue(): A dialogue line with the same ID and language already exists.");
			newDialogueIDTextBox.selectAll();
			newDialogueIDTextBox.setFocus(true);
			return;
		}

		// Clear the text boxes and reset focus
		newDialogueIDTextBox.setText("");
		newDialogueTextBox.setText("");
		newDialogueLanguageTextBox.setText("");
		newDialogueIDTextBox.setFocus(true);

		// Initialise the service proxy
		if (dialogueService == null) {
			dialogueService = GWT.create(DialogueService.class);
		}

		// Add the dialogue line
		LOG.log(Level.INFO, "addDialogue(): calling addLine()");
		dialogueService.addLine(line, new AsyncCallback<Void>() {
			public void onSuccess(Void result) {
				LOG.log(Level.INFO, "addLine(): success");
				addTableRow(line);
			}

			@Override
			public void onFailure(Throwable caught) {
				LOG.log(Level.SEVERE, "addLine(): failure: " + caught.getMessage());
				Window.alert("Error: " + caught.getMessage());
			}
		});
	}

	private void addTableRow(final DialogueLine line) {
		LOG.log(Level.INFO, "addTableRow(): called with " + line.toString());

		lines.add(line);
		int row = dialogueFlexTable.getRowCount();
		dialogueFlexTable.setText(row, 0, Integer.toString(line.getLineId()));
		dialogueFlexTable.setText(row, 1, line.getDialogue());
		dialogueFlexTable.setText(row, 2, line.getLanguage());

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

		dialogueService.deleteLine(line.getId(), new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				LOG.log(Level.INFO, "deleteLine(): success");
				int deletedIndex = lines.indexOf(line);
				lines.remove(deletedIndex);
				dialogueFlexTable.removeRow(deletedIndex + 1);
			}

			@Override
			public void onFailure(Throwable caught) {
				LOG.log(Level.SEVERE, "deleteLine(): failure: " + caught.getMessage());
				Window.alert("Error: " + caught.getMessage());
			}
		});
	}

	private void editDialogue(final DialogueLine line) {
		LOG.log(Level.INFO, "editDialogue(): called with " + line.toString());

		deleteDialogue(line);
		newDialogueIDTextBox.setText(Integer.toString(line.getLineId()));
		newDialogueTextBox.setText(line.getDialogue());
		newDialogueLanguageTextBox.setText(line.getLanguage());
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
					for (int i = 0; i < result.length; i++) {
						DialogueLine line = result[i];
						addTableRow(line);
					}
				}
			}

			@Override
			public void onFailure(Throwable caught) { // TODO: need better error handling of random errors like the "0" error for network problem
				LOG.log(Level.SEVERE, "getLines(): failure: " + caught.getMessage());
				Window.alert("Error: " + caught.getMessage());
			}
		});
	}

}
