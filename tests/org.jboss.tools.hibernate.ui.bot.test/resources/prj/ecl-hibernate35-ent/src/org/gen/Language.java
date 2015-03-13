package org.gen;

// Generated Mar 6, 2015 3:28:00 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Language generated by hbm2java
 */
public class Language implements java.io.Serializable {

	private byte languageId;
	private String name;
	private Date lastUpdate;
	private Set filmsForOriginalLanguageId = new HashSet(0);
	private Set filmsForLanguageId = new HashSet(0);
	private Set filmsForLanguageId_1 = new HashSet(0);
	private Set filmsForOriginalLanguageId_1 = new HashSet(0);

	public Language() {
	}

	public Language(byte languageId, String name, Date lastUpdate) {
		this.languageId = languageId;
		this.name = name;
		this.lastUpdate = lastUpdate;
	}

	public Language(byte languageId, String name, Date lastUpdate,
			Set filmsForOriginalLanguageId, Set filmsForLanguageId,
			Set filmsForLanguageId_1, Set filmsForOriginalLanguageId_1) {
		this.languageId = languageId;
		this.name = name;
		this.lastUpdate = lastUpdate;
		this.filmsForOriginalLanguageId = filmsForOriginalLanguageId;
		this.filmsForLanguageId = filmsForLanguageId;
		this.filmsForLanguageId_1 = filmsForLanguageId_1;
		this.filmsForOriginalLanguageId_1 = filmsForOriginalLanguageId_1;
	}

	public byte getLanguageId() {
		return this.languageId;
	}

	public void setLanguageId(byte languageId) {
		this.languageId = languageId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Set getFilmsForOriginalLanguageId() {
		return this.filmsForOriginalLanguageId;
	}

	public void setFilmsForOriginalLanguageId(Set filmsForOriginalLanguageId) {
		this.filmsForOriginalLanguageId = filmsForOriginalLanguageId;
	}

	public Set getFilmsForLanguageId() {
		return this.filmsForLanguageId;
	}

	public void setFilmsForLanguageId(Set filmsForLanguageId) {
		this.filmsForLanguageId = filmsForLanguageId;
	}

	public Set getFilmsForLanguageId_1() {
		return this.filmsForLanguageId_1;
	}

	public void setFilmsForLanguageId_1(Set filmsForLanguageId_1) {
		this.filmsForLanguageId_1 = filmsForLanguageId_1;
	}

	public Set getFilmsForOriginalLanguageId_1() {
		return this.filmsForOriginalLanguageId_1;
	}

	public void setFilmsForOriginalLanguageId_1(Set filmsForOriginalLanguageId_1) {
		this.filmsForOriginalLanguageId_1 = filmsForOriginalLanguageId_1;
	}

}