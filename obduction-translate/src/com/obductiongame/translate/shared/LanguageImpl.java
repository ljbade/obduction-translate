package com.obductiongame.translate.shared;

import java.io.Serializable;
import java.util.Comparator;

@SuppressWarnings("serial")
public class LanguageImpl implements Serializable, Comparable<LanguageImpl>, Language {

	public static class NameComparator implements Comparator<LanguageImpl> {

		@Override
		public int compare(LanguageImpl arg0, LanguageImpl arg1) {
			return arg0.name.compareTo(arg1.name);
		}

	}

	public static class CodeComparator implements Comparator<LanguageImpl> {

		@Override
		public int compare(LanguageImpl arg0, LanguageImpl arg1) {
			return arg0.code.compareToIgnoreCase(arg1.code);
		}

	}

	private String name;
	private String code;

	public LanguageImpl() {
	}
	
	public LanguageImpl(String code) {
		this(code, "");
	}

	public LanguageImpl(String code, String name) {
		super();
		this.name = name;
		this.code = code.toLowerCase();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
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
		LanguageImpl other = (LanguageImpl) obj;
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
	public int compareTo(LanguageImpl o) {
		return code.compareToIgnoreCase(o.code);
	}

}
