package com.obductiongame.translate.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Composite;
import com.obductiongame.translate.shared.DialogueLine;
import com.obductiongame.translate.shared.Language;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable;

public class EditViewImpl extends Composite implements EditView {

	private static final Logger LOG = Logger.getLogger(EditViewImpl.class.getName());

	private Presenter presenter;

	private final VerticalPanel rootPanel = new VerticalPanel();
	private final FlexTable dialogueFlexTable = new FlexTable();
	
	private final HorizontalPanel addPanel = new HorizontalPanel();
	private final TextBox editIdTextBox = new TextBox();
	private final TextBox editDialogueTextBox = new TextBox();
	private final ListBox editLanguageListBox = new ListBox();
	private final Button addButton = new Button("Add");
	private final Button cancelButton = new Button("Cancel");

	public EditViewImpl() {
		LOG.log(Level.INFO, "EditViewImpl(): called");

		initWidget(rootPanel);

		// Create the table of dialogue lines
		// TODO: need to handle unsync between client and server
		// TODO: need to make consistent group?
		// TODO: need to handle gap between user submit data and it shows up
		// TODO: update when new data is posted?
		rootPanel.add(dialogueFlexTable);

		// Create the add new dialogue panel
		addPanel.add(editIdTextBox);
		addPanel.add(editDialogueTextBox);
		addPanel.add(editLanguageListBox);
		addPanel.add(addButton);
		addPanel.add(cancelButton);
		rootPanel.add(addPanel);

		// Listen for a click on the add button
		addButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				LOG.log(Level.INFO, "addDialogButton.onClick(): called with " + event.toDebugString());
				internalAddDialogue();
			}
		});

		// Listen for enter in the input box.
		editDialogueTextBox.addKeyDownHandler(new KeyDownHandler() {
			public void onKeyDown(KeyDownEvent event) {
				LOG.log(Level.INFO, "addDialogButton.onKeyDown(): called with " + event.toDebugString());
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					internalAddDialogue();
				}
			}
		});

		// Listen for a click on the cancel button
		cancelButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				LOG.log(Level.INFO, "cancelDialogueButton.onClick(): called with " + event.toDebugString());
				editIdTextBox.setText("");
				editDialogueTextBox.setText("");
				editLanguageListBox.setSelectedIndex(0);
				editIdTextBox.setFocus(true);
			}
		});
	}

	private void internalAddDialogue() {
		LOG.log(Level.INFO, "internalAddDialogue(): called");
		presenter.addDialogue(editIdTextBox.getText(), editDialogueTextBox.getText(), editLanguageListBox.getValue(editLanguageListBox.getSelectedIndex()));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		LOG.log(Level.INFO, "setPresenter(): called with " + presenter);
		this.presenter = presenter;
	}

	@Override
	public void reset() {
		LOG.log(Level.INFO, "reset(): called");

		// Create the table header
		dialogueFlexTable.removeAllRows();
		dialogueFlexTable.setText(0, 0, "ID");
		dialogueFlexTable.setText(0, 1, "Dialogue");
		dialogueFlexTable.setText(0, 2, "Language");
		dialogueFlexTable.setText(0, 3, "Edit");
		dialogueFlexTable.setText(0, 4, "Delete");

		// Clear the add inputs
		clearDialogue();

		// Set the focus to the text box
		editIdTextBox.setFocus(true);
	}

	@Override
	public void addDialogue(DialogueLine line) {
		LOG.log(Level.INFO, "addDialogue(): called with " + line.toString());
		createTableRow(line, dialogueFlexTable.getRowCount() - 1);
	}

	@Override
	public void insertDialogue(DialogueLine line, int index) {
		LOG.log(Level.INFO, "insertDialogue(): called with " + line.toString() + ", " + Integer.toString(index));
		createTableRow(line, index);
	}
//
	private void createTableRow(final DialogueLine line, final int index) {
		LOG.log(Level.INFO, "createTableRow(): called with " + line.toString() + ", " + Integer.toString(index));

		dialogueFlexTable.insertRow(index + 1);
		dialogueFlexTable.setText(index + 1, 0, Integer.toString(line.getId()));
		dialogueFlexTable.setText(index + 1, 1, line.getDialogue());
		dialogueFlexTable.setText(index + 1, 2, line.getLanguage());

		// Add the edit button
		// TODO: make work in place and not delete before add
		Button editDialogueButton = new Button("Edit");
		editDialogueButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				LOG.log(Level.INFO, "editDialogueButton.onClick(): called with " + event.toDebugString());
				presenter.editDialogue(index);
			}
		});
		dialogueFlexTable.setWidget(index + 1, 3, editDialogueButton);

		// Add the delete button
		Button deleteDialogueButton = new Button("Delete");
		deleteDialogueButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				LOG.log(Level.INFO, "deleteDialogueButton.onClick(): called with " + event.toDebugString());
				presenter.deleteDialogue(index);
			}
		});
		dialogueFlexTable.setWidget(index + 1, 4, deleteDialogueButton);
	}

	@Override
	public void deleteDialogue(int index) {
		LOG.log(Level.INFO, "deleteDialogue(): called with " + Integer.toString(index));
		dialogueFlexTable.removeRow(index + 1);
	}

	@Override
	public void editDialogue(DialogueLine line) {
		LOG.log(Level.INFO, "editDialogue(): called with " + line.toString());
		editIdTextBox.setText(Integer.toString(line.getId()));
		editDialogueTextBox.setText(line.getDialogue());
		editLanguageListBox.setSelectedIndex(0);
		boolean found = false;
		for (int i = 0; i < editLanguageListBox.getItemCount(); i++) { // TODO: linear search too slow
			if (line.getLanguage().equals(editLanguageListBox.getValue(i))) {
				editLanguageListBox.setSelectedIndex(i);
				found = true;
				break;
			}
		}
		if (!found) {
			editLanguageListBox.setSelectedIndex(0);
			LOG.log(Level.WARNING, "editDialogue(): language '" + line.getLanguage() + "' not found");
		}
		editDialogueTextBox.selectAll();
		editDialogueTextBox.setFocus(true);
	}

	@Override
	public void addLanguage(Language language) {
		LOG.log(Level.INFO, "addLanguage(): called with " + language.toString());
		editLanguageListBox.addItem(language.getName(), language.getCode());
	}

	@Override
	public void setDialogueLanguage(String language, int index) {
		LOG.log(Level.INFO, "setDialogueLanguage(): called with " + language.toString() + ", " + Integer.toString(index));
		dialogueFlexTable.setText(index + 1, 2, language);
	}

	@Override
	public void selectDialogueId() {
		LOG.log(Level.INFO, "selectDialogueId(): called");
		editIdTextBox.selectAll();
		editIdTextBox.setFocus(true);
	}

	@Override
	public void clearDialogue() {
		LOG.log(Level.INFO, "clearDialogue(): called");
		editIdTextBox.setText("");
		editDialogueTextBox.setText("");
		editLanguageListBox.setSelectedIndex(0);
		editIdTextBox.setFocus(true);
		presenter.clearDialogue();
	}

}
