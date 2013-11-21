package com.obductiongame.translate.shared;

import java.io.Serializable;
import java.util.Comparator;

@SuppressWarnings("serial")
public class Language implements Serializable, Comparable<Language> {

	public static class NameComparator implements Comparator<Language> {

		@Override
		public int compare(Language arg0, Language arg1) {
			return arg0.name.compareTo(arg1.name);
		}

	}

	public static class CodeComparator implements Comparator<Language> {

		@Override
		public int compare(Language arg0, Language arg1) {
			return arg0.code.compareToIgnoreCase(arg1.code);
		}

	}

	private String name;
	private String code;

	@SuppressWarnings("unused")
	private Language() {
	}
	
	public Language(String code) {
		this(code, "");
	}

	public Language(String code, String name) {
		super();
		this.name = name;
		this.code = code.toLowerCase();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.toLowerCase().hashCode());
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
		Language other = (Language) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equalsIgnoreCase(other.code))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Language [name=" + name + ", code=" + code + "]";
	}

	@Override
	public int compareTo(Language o) {
		return code.compareToIgnoreCase(o.code);
	}

}
