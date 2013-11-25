package com.obductiongame.translate.server.entity;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
@Inheritance(customStrategy = "complete-table")
@Version(strategy = VersionStrategy.VERSION_NUMBER,
	extensions = {@Extension(vendorName = "datanucleus", key = "field-name", value = "version")})
public abstract class Entity {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	protected Key key;
	@Persistent
	protected long version;

	public Entity() {
		super();
	}

	public Entity(Class<Entity> clazz, String name) {
		this(KeyFactory.createKey(clazz.getSimpleName(), name));
	}

	public Entity(Class<Entity> clazz, long id) {
		this(KeyFactory.createKey(clazz.getSimpleName(), id));
	}

	public Entity(Key key) {
		super();
		this.key = key;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		throw new UnsupportedOperationException("The entity's version cannot be changed.");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + (int) (version ^ (version >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Entity)) {
			return false;
		}
		Entity other = (Entity) obj;
		if (key == null) {
			if (other.key != null) {
				return false;
			}
		} else if (!key.equals(other.key)) {
			return false;
		}
		if (version != other.version) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Entity [key=" + key + ", version=" + version + "]";
	}

}