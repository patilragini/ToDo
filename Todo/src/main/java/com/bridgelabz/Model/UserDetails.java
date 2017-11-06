package com.bridgelabz.Model;

import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * 
 * @author Ragini pojo user_Details
 *
 */
@Entity
@Table(name = "User_Detail")
public class UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int id;

	private String name;
	private String phoneNumber;

	@Column(unique = true)
	private String email;

	public Set<Notes> getNotes() {
		return notes;
	}

	@OneToMany(mappedBy = "User_Details")
	public void setNotes(Set<Notes> notes) {
		this.notes = notes;
	}

	private String password;

	private Set<Notes> notes = new HashSet<Notes>();

	public int getActivated() {
		return activated;
	}

	public void setActivated(int activated) {
		this.activated = activated;
	}

	@Column(name = "activated", updatable = true)
	private int activated;

	// id,name,phonenumber,password
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserDetails [id=" + id + ", name=" + name + ", password=" + password + ", phoneNumber=" + phoneNumber
				+ ", email=" + email + "]";
	}

}
