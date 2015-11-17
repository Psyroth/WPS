package com.example.wps.db;

import java.util.Comparator;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Account {
	
	private String name;
	private String id;
	private String password;
	private String url;
	private String lastAccess;
	private String note;
	private String category;
	private Boolean isFavorite;

	public Account(String name, String id, String password, String url,
			String lastAccess, String note, String category, Boolean isFavorite) {
		this.name = name;
		this.id = id;
		this.password = password;
		this.url = url;
		this.lastAccess = lastAccess;
		this.note = note;
		this.category = category;
		this.isFavorite = isFavorite;
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

	public Boolean getIsFavorite() {
		return isFavorite;
	}

	public void setIsFavorite(Boolean isFavorite) {
		this.isFavorite = isFavorite;
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
					.getCategory() + "\nIs Favorite : " + this.getIsFavorite().toString());
	}

	public static Comparator<Account> COMPARE_BY_NAME = new Comparator<Account>() {
		public int compare(Account one, Account other) {
			return (one.getName().compareTo(other.getName()));
		}
	};

	public static Comparator<Account> COMPARE_BY_LASTACCESS = new Comparator<Account>() {
		public int compare(Account one, Account other) {
			DateTimeFormatter formatter = DateTimeFormat
					.forPattern("yyyy-MM-dd HH:mm:ss");

			DateTime lastAccess1 = formatter.parseDateTime(one.getLastAccess());
			DateTime lastAccess2 = formatter.parseDateTime(other
					.getLastAccess());

			// -1 this is before another
			// 0 this has the same date that another
			// 1 this is after another

			return (lastAccess1.compareTo(lastAccess2));
		}
	};
}