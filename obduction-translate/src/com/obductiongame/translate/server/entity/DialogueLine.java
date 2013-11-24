package com.obductiongame.translate.server.entity;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

@SuppressWarnings("serial")
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
@Version(strategy = VersionStrategy.VERSION_NUMBER,
	extensions = { @Extension(vendorName = "datanucleus", key = "field-name", value = "version") })
public class DialogueLine implements Serializable, Comparable<DialogueLine> {
	// TODO: use http://www.resmarksystems.com/code/
	// TODO: https://code.google.com/p/objectify-appengine/?
	// TODO: use ICU4J?

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String key;
	@Persistent
	private long version;
	@Persistent
	private int id;
	@Persistent
	private String dialogue;
	@Persistent
	private String language;

	public DialogueLine() {
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
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
//TODO: change all the functions after conversion to entity proxy?
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((language == null) ? 0 : language.hashCode());
		result = prime * result + id;
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
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DialogueLine [id=" + key + ", lineId=" + id + ", dialogue="
				+ dialogue + ", language=" + language + "]";
	}

	@Override
	public int compareTo(DialogueLine o) {
		if (id == o.id) {
			return language.compareToIgnoreCase(o.language);
		}
		return id - o.id;
	}

}