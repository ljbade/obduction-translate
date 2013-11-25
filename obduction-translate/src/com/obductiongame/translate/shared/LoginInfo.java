package com.obductiongame.translate.shared;

import java.io.Serializable;

public class LoginInfo implements Serializable {

	private static final long serialVersionUID = 1453092868307224121L;

	private boolean loggedIn;
	private boolean admin;
	private String loginUrl;
	private String logoutUrl;
	private String name;
	private String email;

	@SuppressWarnings("unused")
	private LoginInfo() {
		super();
	}

	public LoginInfo(boolean admin, String logoutUrl, String name, String email) {
		loggedIn = true;
		this.admin = admin;
		this.logoutUrl = logoutUrl;
		this.name = name;
		this.email = email;
	}

	public LoginInfo(String loginUrl) {
		loggedIn = false;
		this.loginUrl = loginUrl;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (admin ? 1231 : 1237);
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (loggedIn ? 1231 : 1237);
		result = prime * result
				+ ((loginUrl == null) ? 0 : loginUrl.hashCode());
		result = prime * result
				+ ((logoutUrl == null) ? 0 : logoutUrl.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (!(obj instanceof LoginInfo)) {
			return false;
		}
		LoginInfo other = (LoginInfo) obj;
		if (admin != other.admin) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (loggedIn != other.loggedIn) {
			return false;
		}
		if (loginUrl == null) {
			if (other.loginUrl != null) {
				return false;
			}
		} else if (!loginUrl.equals(other.loginUrl)) {
			return false;
		}
		if (logoutUrl == null) {
			if (other.logoutUrl != null) {
				return false;
			}
		} else if (!logoutUrl.equals(other.logoutUrl)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "LoginInfo [loggedIn=" + loggedIn + ", admin=" + admin
				+ ", loginUrl=" + loginUrl + ", logoutUrl=" + logoutUrl
				+ ", name=" + name + ", email=" + email + "]";
	}

}
