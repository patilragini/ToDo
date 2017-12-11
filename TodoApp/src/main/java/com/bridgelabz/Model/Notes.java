package com.bridgelabz.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Notes_Detail")
public class Notes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "note_id")
	private int id;
	
	private String title;
	
	private String description;
	
	private String color;
	
	

	private Date createDate;
	
	private Date lastUpdated;
	
	private boolean isPinned;
	
	private boolean inTrash;

	private boolean isArchive;
	
	private Date remainder;
	
	@Column(columnDefinition = "LONGBLOB")
	private String image;
	

	private boolean reminderStatus;
	
	
	
	@ManyToMany(fetch=FetchType.EAGER)
	@Column(name = "label_id")
	private Set<Label> labels;
	
	public Set<Label> getLabels() {
		return labels;
	}
	
	public void setLabels(Set<Label> labels) {
		this.labels = labels;
	}
	
	
	public boolean isReminderStatus() {
		return reminderStatus;
	}

	public void setReminderStatus(boolean reminderStatus) {
		this.reminderStatus = reminderStatus;
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Date getRemainder() {
		return remainder;
	}
	public void setRemainder(Date remainder) {
		this.remainder = remainder;
	}
	public boolean isArchive() {
		return isArchive;
	}
	public void setArchive(boolean isArchive) {
		this.isArchive = isArchive;
	}
	public boolean isPinned() {
		return isPinned;
	}
	public void setPinned(boolean isPinned) {
		this.isPinned = isPinned;
	}
	public boolean isInTrash() {
		return inTrash;
	}
	public void setInTrash(boolean inTrash) {
		this.inTrash = inTrash;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
/*	@JsonIgnore
	@OneToMany
	@JoinColumn(name="user_id")
	private UserDetails userShareDetails;
*/
	
	@JsonIgnore
	@ManyToOne
    @JoinColumn(name="user_id")
	private UserDetails userDetails;

	
	public UserDetails getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
		
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
}
