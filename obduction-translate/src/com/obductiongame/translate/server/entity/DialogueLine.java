package com.obductiongame.translate.server.entity;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable(detachable = "true")
public class DialogueLine extends Entity {

	@Persistent
	@Min(1)
	private int id;
	@Persistent
	@NotNull
	@Size(min=1)
	private String dialogue;
	@Persistent
	@NotNull
	@Size(min=2, max=3)
	private String language;

	public DialogueLine() {
		super();
	}

	public DialogueLine(int id, String dialogue, String language) {
		super();
		this.id = id;
		this.dialogue = dialogue;
		this.language = language;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDialogue() {
		return dialogue;
	}

	public void setDialogue(String dialogue) {
		this.dialogue = dialogue;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((dialogue == null) ? 0 : dialogue.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((language == null) ? 0 : language.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof DialogueLine)) {
			return false;
		}
		DialogueLine other = (DialogueLine) obj;
		if (dialogue == null) {
			if (other.dialogue != null) {
				return false;
			}
		} else if (!dialogue.equals(other.dialogue)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (language == null) {
			if (other.language != null) {
				return false;
			}
		} else if (!language.equals(other.language)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "DialogueLine [id=" + id + ", dialogue=" + dialogue
				+ ", language=" + language + ", key=" + key + ", version="
				+ version + "]";
	}

	public String toName() {
		return toName(id, language);
	}

	public Key toKey() {
		return toKey(id, language);
	}

	public static String toName(int id, String language) {
		return Integer.toString(id) + "," + language;
	}

	public static Key toKey(int id, String language) {
		return KeyFactory.createKey(DialogueLine.class.getSimpleName(), toName(id, language));
	}

}
