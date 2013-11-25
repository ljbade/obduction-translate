package com.obductiongame.translate.server.entity;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable(detachable = "true")
public class DialogueLine extends Entity {

	@Persistent
	private int id;
	@Persistent
	private String dialogue;
	@Persistent
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

}
