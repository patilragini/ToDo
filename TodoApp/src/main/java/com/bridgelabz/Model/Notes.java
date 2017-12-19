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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The Class Notes.
 */
@Entity
@Table(name = "Notes_Detail")
public class Notes {

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "note_id")
	private int id;

	/** The title. */
	private String title;

	/** The description. */
	private String description;

	/** The color. */
	private String color;

	/** The create date. */
	private Date createDate;

	/** The last updated. */
	private Date lastUpdated;

	/** The is pinned. */
	private boolean isPinned;

	/** The in trash. */
	private boolean inTrash;

	/** The is archive. */
	private boolean isArchive;

	/** The remainder. */
	private Date remainder;

	/** The image. */
	@Column(columnDefinition = "LONGBLOB")
	private String image;

	/** The reminder status. */
	private boolean reminderStatus;
/*
	@ManyToMany(fetch = FetchType.EAGER)
	@Column(name = "notes")
	private Images images;

	public Images getImages() {
		return images;
	}

	public void setImages(Images images) {
		this.images = images;
	}*/
	/*@OneToOne(fetch = FetchType.EAGER)
	@Column(name = "notes")
	private Images images;

	public Images getImages() {
		return images;
	}

	public void setImages(Images images) {
		this.images = images;
	}*/
	
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "notes_img")
	private Images images;

	public Images getImages() {
		return images;
	}

	public void setImages(Images images) {
		this.images = images;
	}
	
	
	
	
	/** The labels. */
	@ManyToMany(fetch = FetchType.EAGER)
	@Column(name = "label_id")
	private Set<Label> labels;

	/**
	 * Gets the labels.
	 *
	 * @return the labels
	 */
	public Set<Label> getLabels() {
		return labels;
	}

	/**
	 * Sets the labels.
	 *
	 * @param labels
	 *            the new labels
	 */
	public void setLabels(Set<Label> labels) {
		this.labels = labels;
	}

	/**
	 * Checks if is reminder status.
	 *
	 * @return true, if is reminder status
	 */
	public boolean isReminderStatus() {
		return reminderStatus;
	}

	/**
	 * Sets the reminder status.
	 *
	 * @param reminderStatus
	 *            the new reminder status
	 */
	public void setReminderStatus(boolean reminderStatus) {
		this.reminderStatus = reminderStatus;
	}

	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * Sets the image.
	 *
	 * @param image
	 *            the new image
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * Gets the remainder.
	 *
	 * @return the remainder
	 */
	public Date getRemainder() {
		return remainder;
	}

	/**
	 * Sets the remainder.
	 *
	 * @param remainder
	 *            the new remainder
	 */
	public void setRemainder(Date remainder) {
		this.remainder = remainder;
	}

	/**
	 * Checks if is archive.
	 *
	 * @return true, if is archive
	 */
	public boolean isArchive() {
		return isArchive;
	}

	/**
	 * Sets the archive.
	 *
	 * @param isArchive
	 *            the new archive
	 */
	public void setArchive(boolean isArchive) {
		this.isArchive = isArchive;
	}

	/**
	 * Checks if is pinned.
	 *
	 * @return true, if is pinned
	 */
	public boolean isPinned() {
		return isPinned;
	}

	/**
	 * Sets the pinned.
	 *
	 * @param isPinned
	 *            the new pinned
	 */
	public void setPinned(boolean isPinned) {
		this.isPinned = isPinned;
	}

	/**
	 * Checks if is in trash.
	 *
	 * @return true, if is in trash
	 */
	public boolean isInTrash() {
		return inTrash;
	}

	/**
	 * Sets the in trash.
	 *
	 * @param inTrash
	 *            the new in trash
	 */
	public void setInTrash(boolean inTrash) {
		this.inTrash = inTrash;
	}

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Sets the color.
	 *
	 * @param color
	 *            the new color
	 */
	public void setColor(String color) {
		this.color = color;
	}
	/*
	 * @JsonIgnore
	 * 
	 * @OneToMany
	 * 
	 * @JoinColumn(name="user_id") private UserDetails userShareDetails;
	 */

	/** The user details. */
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserDetails userDetails;

	/**
	 * Gets the user details.
	 *
	 * @return the user details
	 */
	public UserDetails getUserDetails() {
		return userDetails;
	}

	/**
	 * Sets the user details.
	 *
	 * @param userDetails
	 *            the new user details
	 */
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the creates the date.
	 *
	 * @return the creates the date
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate
	 *            the new creates the date
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the last updated.
	 *
	 * @return the last updated
	 */
	public Date getLastUpdated() {
		return lastUpdated;
	}

	/**
	 * Sets the last updated.
	 *
	 * @param lastUpdated
	 *            the new last updated
	 */
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}
