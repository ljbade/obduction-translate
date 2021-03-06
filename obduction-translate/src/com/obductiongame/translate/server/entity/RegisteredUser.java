package com.obductiongame.translate.server.entity;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable(detachable = "true")
public class RegisteredUser extends Entity {

	@Persistent
	private String id;

	public RegisteredUser() {
		super();
	}

	public RegisteredUser(String id) {
		super(toKey(id));
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (!(obj instanceof RegisteredUser)) {
			return false;
		}
		RegisteredUser other = (RegisteredUser) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "RegisteredUser [id=" + id + ", key=" + key
				+ ", version=" + version + "]";
	}

	public String toName() {
		return toName(id);
	}

	public Key toKey() {
		return toKey(id);
	}

	public static String toName(String id) {
		return id;
	}

	public static Key toKey(String id) {
		return KeyFactory.createKey(RegisteredUser.class.getSimpleName(), toName(id));
	}

}
