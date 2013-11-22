package com.obductiongame.translate.client;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import com.obductiongame.translate.shared.DialogueLine;
import com.obductiongame.translate.shared.Language;

public interface EditView extends IsWidget {

	void setPresenter(Presenter presenter);
	void reset();

	void addDialogue(DialogueLine line);
	void insertDialogue(DialogueLine line, int index);
	void deleteDialogue(int index);
	void editDialogue(DialogueLine line);
	void setDialogueLanguage(String language, int index);

	void selectDialogueId();
	void clearDialogue();

	void addLanguage(Language language);

	public interface Presenter {
		void goTo(Place place);

		void addDialogue(String idText, String dialogue, String language);
		void deleteDialogue(int index);
		void editDialogue(int index);
		void clearDialogue();
	}

}
