package com.obductiongame.translate.server.entity;

import javax.jdo.annotations.Persistent;
import javax.validation.constraints.NotNull;

import com.google.appengine.api.datastore.Key;

public abstract class UserRequiredEntity extends Entity {
//TODO add bean validation to all entities and proxies
	@Persistent
	@NotNull
	private RegisteredUser user;

	public UserRequiredEntity() {
		super();
	}

	public UserRequiredEntity(Class<Entity> clazz, String name, RegisteredUser user) {
		super(clazz, name);
		this.user = user;
	}

	public UserRequiredEntity(Class<Entity> clazz, long id, RegisteredUser user) {
		super(clazz, id);
		this.user = user;
	}

	public UserRequiredEntity(Key key, RegisteredUser user) {
		super(key);
		this.user = user;
	}

	public RegisteredUser getUser() {
		return user;
	}

	public void setUser(RegisteredUser user) {
		throw new UnsupportedOperationException("The entity's user cannot be changed.");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		if (!(obj instanceof UserRequiredEntity)) {
			return false;
		}
		UserRequiredEntity other = (UserRequiredEntity) obj;
		if (user == null) {
			if (other.user != null) {
				return false;
			}
		} else if (!user.equals(other.user)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "UserRequiredEntity [user=" + user + ", key=" + key
				+ ", version=" + version + "]";
	}

}
