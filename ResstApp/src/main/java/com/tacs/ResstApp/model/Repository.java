package com.tacs.ResstApp.model;

import java.util.Date;

public class Repository {

	private Long id;

	private String name;

	private Date registrationDate;

	public Repository(Long id, String name){
		this.id = id;
		this.name = name;
		this.registrationDate = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}



}
