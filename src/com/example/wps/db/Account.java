package com.example.wps.db;

public class Account {
	private String name;
	private String id;
	private String password;
	private String url;
	private String lastAccess;
	private String note;
	private String category;

	public Account(String name, String id, String password, String url,
			String lastAccess, String note, String category) {
		this.name = name;
		this.id = id;
		this.password = password;
		this.url = url;
		this.lastAccess = lastAccess;
		this.note = note;
		this.category = category;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String toXmlString() {
		return ("<" + this.getName() + "Account><name>" + this.getName()
				+ "</name><id>" + this.getId() + "</id><password>"
				+ this.getPassword() + "</password><url>" + this.getUrl()
				+ "</url><lastAccess>" + this.getLastAccess()
				+ "</lastAccess><note>" + this.getNote() + "</note><category>"
				+ this.getCategory() + "</category></" + this.getName() + "Account>");
	}

	@Override
	public String toString() {
		return ("\nName : " + this.getName() + "\nId : " + this.getId()
				+ "\nPassword : " + this.getPassword() + "\nUrl : "
				+ this.getUrl() + "\nLast Access : " + this.getLastAccess()
				+ "\nNote : " + this.getNote() + "\nCategory : " + this
					.getCategory());
	}
}