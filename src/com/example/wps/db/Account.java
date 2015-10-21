package com.example.wps.db;

public class Account {
	private String name;
	private String id;
	private String password;
	private String url;
	private String lastAccess;
	private String note;

	public Account(String name, String id, String password, String url,
			String lastAccess, String note) {
		this.name = name;
		this.id = id;
		this.password = password;
		this.url = url;
		this.lastAccess = lastAccess;
		this.note = note;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(String lastAccess) {
		this.lastAccess = lastAccess;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}