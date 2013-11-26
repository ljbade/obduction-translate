package com.obductiongame.translate.server.entity;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable(detachable = "true")
public abstract class UserRequiredEntity extends Entity {

	@Persistent
	@Extension(vendorName = "datanucleus", key = "gae.parent-pk", value = "true") 
	private Key userKey;

	public UserRequiredEntity() {
		super();
	}

	public UserRequiredEntity(Class<? extends UserRequiredEntity> clazz, String name, Key userKey) {
		super(clazz, name);
		this.userKey = userKey;
	}

	public UserRequiredEntity(Class<? extends UserRequiredEntity> clazz, long id, Key userKey) {
		super(clazz, id);
		this.userKey = userKey;
	}

	public UserRequiredEntity(Key key, Key userKey) {
		super(key);
		this.userKey = userKey;
	}

	public Key getUserKey() {
		return userKey;
	}

	public void setUserKey(Key userKey) {
		this.userKey = userKey;
	}

	public String getEncodedUserKey() {
		return KeyFactory.keyToString(userKey);
	}

	public void setEncodedUserKey(String encodedUserKey) {
		key = KeyFactory.stringToKey(encodedUserKey);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((userKey == null) ? 0 : userKey.hashCode());
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
		if (userKey == null) {
			if (other.userKey != null) {
				return false;
			}
		} else if (!userKey.equals(other.userKey)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "UserRequiredEntity [userKey=" + userKey + ", key=" + key
				+ ", version=" + version + "]";
	}

}
