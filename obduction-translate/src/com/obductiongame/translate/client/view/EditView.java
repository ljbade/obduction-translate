package com.obductiongame.translate.client.view;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.obductiongame.translate.client.proxy.DialogueLineProxy;
import com.obductiongame.translate.shared.Language;

public interface EditView extends IsWidget {

	void setPresenter(Presenter presenter);
	void reset();

	void addDialogue(DialogueLineProxy line);
	void insertDialogue(DialogueLineProxy line, int index);
	void deleteDialogue(int index);
	void editDialogue(DialogueLineProxy line);
	void setDialogueLanguage(String language, int index);

	void selectDialogueId();
	void clearDialogue();

	void addLanguage(Language language);

	public interface Presenter {
		void goTo(Place place);

		void addDialogue(String idText, String dialogue, String language);
		void deleteDialogue(DialogueLineProxy line);
		void editDialogue(DialogueLineProxy line);
		void clearDialogue();
	}

}
