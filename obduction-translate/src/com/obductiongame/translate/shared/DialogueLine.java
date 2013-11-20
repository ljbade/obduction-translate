package com.obductiongame.translate.shared;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class DialogueLine implements Serializable{

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private int lineId;
	@Persistent
	private String dialogue;
	@Persistent
	private String language;

	@SuppressWarnings("unused")
	private DialogueLine() {
	}

	public DialogueLine(int lineId, String dialogue, String language) {
		super();
		this.lineId = lineId;
		this.dialogue = dialogue;
		this.language = language;
	}

	public Long getId() {
		return id;
	}

	public int getLineId() {
		return lineId;
	}

	public void setLineId(int lineId) {
		this.lineId = lineId;
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
		int result = 1;
		result = prime * result
				+ ((dialogue == null) ? 0 : dialogue.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((language == null) ? 0 : language.hashCode());
		result = prime * result + lineId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DialogueLine other = (DialogueLine) obj;
		if (dialogue == null) {
			if (other.dialogue != null)
				return false;
		} else if (!dialogue.equals(other.dialogue))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (lineId != other.lineId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DialogueLine [id=" + id + ", lineId=" + lineId + ", dialogue="
				+ dialogue + ", language=" + language + "]";
	}

	

}
