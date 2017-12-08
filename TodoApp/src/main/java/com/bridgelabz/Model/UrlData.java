package com.bridgelabz.Model;

//TODO: Auto-generated Javadoc
/**
* The Class UrlData.
*/
public class UrlData {

	/** The title. */
	private String title;
	
	/** The image url. */
	private String imageUrl;
	
	/** The domain. */
	private String domain;
	
	/**
	 * Instantiates a new url data.
	 *
	 * @param title the title
	 * @param imageUrl the image url
	 * @param domain the domain
	 */
	public  UrlData(String title,String imageUrl, String domain){
		this.title=title;
		this.imageUrl=imageUrl;
		this.domain=domain;
	}

	/**
	 * Gets the domain.
	 *
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * Sets the domain.
	 *
	 * @param domain the new domain
	 */
	public void setDomain(String domain) {
		this.domain = domain;
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
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the image url.
	 *
	 * @return the image url
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * Sets the image url.
	 *
	 * @param imageUrl the new image url
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
}
