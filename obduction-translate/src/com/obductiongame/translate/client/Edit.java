package com.obductiongame.translate.client;
import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.obductiongame.translate.shared.DialogueLine;

public class Edit implements EntryPoint {
	
	private final FlexTable dialogueFlexTable = new FlexTable();
	private final VerticalPanel mainPanel = new VerticalPanel();
	
	private final TextBox newDialogueIDTextBox = new TextBox();
	private final TextBox newDialogueTextBox = new TextBox();
	private final TextBox newDialogueLanguageTextBox = new TextBox();
	private final Button addDialogueButton = new Button("Add");
	private final Button cancelDialogueButton = new Button("Cancel");
	private final HorizontalPanel addPanel = new HorizontalPanel();
	
	private final ArrayList<DialogueLine> lines = new ArrayList<DialogueLine>();
	
	public void onModuleLoad() {
		// Create the table of dialogue lines
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
				addDialogue(false);
			}
		});
		
		// Listen for enter in the input box.
		newDialogueTextBox.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					addDialogue(false);
				}
			}
		});
		
		// Listen for a click on the cancel button
		cancelDialogueButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				newDialogueIDTextBox.setText("");
				newDialogueTextBox.setText("");
				newDialogueLanguageTextBox.setText("");
				newDialogueIDTextBox.setFocus(true);
			}
		});
	}

	private void addDialogue(boolean editing) {
		
		// Check ID is a integer
		int id;
		try {
			id = Integer.parseInt(newDialogueIDTextBox.getText());
		} catch (NumberFormatException e) {
			Window.alert("'" + newDialogueIDTextBox.getText() + "' is not a valid ID.");
			newDialogueIDTextBox.selectAll();
			newDialogueIDTextBox.setFocus(true);
			return;
		}
		
		final DialogueLine line = new DialogueLine(id, newDialogueTextBox.getText(), newDialogueLanguageTextBox.getText());
		
		// Check an identical dialogue line is not already present
		if (lines.contains(line)) {
			Window.alert("A dialogue line with the same ID and language already exists.");
			newDialogueIDTextBox.selectAll();
			newDialogueIDTextBox.setFocus(true);
			return;
		}
		
		// Clear the text boxes and reset focus
		newDialogueIDTextBox.setText("");
		newDialogueTextBox.setText("");
		newDialogueLanguageTextBox.setText("");
		newDialogueIDTextBox.setFocus(true);
		
		// Add the dialogue line
		lines.add(line);
		int row = dialogueFlexTable.getRowCount();
		dialogueFlexTable.setText(row, 0, Integer.toString(line.getID()));
		dialogueFlexTable.setText(row, 1, line.getDialogue());
		dialogueFlexTable.setText(row, 2, line.getLanguage());
		
		// Add the edit button
		// TODO: make work in place and not delete before add
		// add cancel button
		Button editDialogueButton = new Button("Edit");
		editDialogueButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int editedIndex = lines.indexOf(line);
				lines.remove(editedIndex);
				dialogueFlexTable.removeRow(editedIndex + 1);
				newDialogueIDTextBox.setText(Integer.toString(line.getID()));
				newDialogueTextBox.setText(line.getDialogue());
				newDialogueLanguageTextBox.setText(line.getLanguage());
				newDialogueTextBox.selectAll();
				newDialogueTextBox.setFocus(true);
			}
		});
		dialogueFlexTable.setWidget(row, 3, editDialogueButton);
		
		// Add the delete button
		Button deleteDialogueButton = new Button("Delete");
		deleteDialogueButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				int deletedIndex = lines.indexOf(line);
				lines.remove(deletedIndex);
				dialogueFlexTable.removeRow(deletedIndex + 1);
			}
		});
		dialogueFlexTable.setWidget(row, 4, deleteDialogueButton);
		
	}
}
