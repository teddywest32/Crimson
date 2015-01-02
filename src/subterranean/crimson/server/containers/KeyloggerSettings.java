package subterranean.crimson.server.containers;

import java.io.Serializable;

public class KeyloggerSettings implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private boolean highlightPhone;

	/**
	 * 
	 */
	private boolean highlightEmail;

	/**
	 * 
	 */
	private boolean highlightURL;

	/**
	 * 
	 */
	private boolean highlightFileURL;

	public boolean isHighlightPhone() {
		return highlightPhone;
	}

	public void setHighlightPhone(boolean highlightPhone) {
		this.highlightPhone = highlightPhone;
	}

	public boolean isHighlightEmail() {
		return highlightEmail;
	}

	public void setHighlightEmail(boolean highlightEmail) {
		this.highlightEmail = highlightEmail;
	}

	public boolean isHighlightURL() {
		return highlightURL;
	}

	public void setHighlightURL(boolean highlightURL) {
		this.highlightURL = highlightURL;
	}

	public boolean isHighlightFileURL() {
		return highlightFileURL;
	}

	public void setHighlightFileURL(boolean highlightFileURL) {
		this.highlightFileURL = highlightFileURL;
	}

}
