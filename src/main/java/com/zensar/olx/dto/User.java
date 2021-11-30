package com.zensar.olx.dto;

public class User {
	private int id;
	private String username;
	private String roles;

	public User() {
	}

	public User(int id, String username, String roles) {
		super();
		this.id = id;
		this.username = username;
		this.roles = roles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
