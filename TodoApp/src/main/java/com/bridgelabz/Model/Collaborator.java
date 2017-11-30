package com.bridgelabz.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.bridgelabz.Model.Notes;

@Entity
@Table
public class Collaborator{
	@Id
	@GenericGenerator(name = "abc", strategy = "increment")
	@GeneratedValue(generator = "abc")
	private int collaboratorId;
	
	@ManyToOne
	@JoinColumn(name="ownerId")
	private UserDetails ownerId;
	
	@ManyToOne
	@JoinColumn(name="shareWithId")
	private UserDetails shareWithId;
	
	@ManyToOne
	@JoinColumn(name="noteId")
	private Notes note;

	public int getCollaboratorId() {
		return collaboratorId;
	}

	public UserDetails getOwnerId() {
		return ownerId;
	}

	public UserDetails getShareWithId() {
		return shareWithId;
	}

	public Notes getNote() {
		return note;
	}

	public void setCollaboratorId(int collaboratorId) {
		this.collaboratorId = collaboratorId;
	}

	public void setOwnerId(UserDetails ownerId) {
		this.ownerId = ownerId;
	}

	public void setShareWithId(UserDetails shareWithId) {
		this.shareWithId = shareWithId;
	}

	public void setNote(Notes note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "Collaborator [collaboratorId=" + collaboratorId + ", ownerId=" + ownerId + ", shareWithId="
				+ shareWithId + ", note=" + note + "]";
	}
}
