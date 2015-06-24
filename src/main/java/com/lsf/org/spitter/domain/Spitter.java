package com.lsf.org.spitter.domain;

import java.util.List;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class Spitter {
	private String id;
	@Size(min=3,max=20,message="userName must be between 3 and 20 characters long.")
	@Pattern(regexp="^[a-zA-Z0-9]+$",message="username must be alphanmuser with no spaces")
	private String username;
	@Size(min=6,max=20,message="The passWord must be at least 6 characters long")
	private String password;
	@Size(min=3,max=50,message="your full name must bu between 3 and 50 characters long")
	private String fullName;
	private List<Spittle> spittles;
	@Pattern(regexp="[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}",message="Invalid email address")
	private String email;
	private boolean updateByEmail;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setSpittles(List<Spittle> spittles) {
		this.spittles = spittles;
	}

	public List<Spittle> getSpittles() {
		return spittles;
	}

	public void setUpdateByEmail(boolean updateByEmail) {
		this.updateByEmail = updateByEmail;
	}

	public boolean isUpdateByEmail() {
		return updateByEmail;
	}

	@Override
	public boolean equals(Object obj) {
		Spitter other = (Spitter) obj;
		return other.fullName.equals(fullName)
				&& other.username.equals(username)
				&& other.password.equals(password);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
}
