package com.vrshah.moviedb.beans;

public class Movie {
	private int id;
	private String title;
	private int year;
	private String Director;
	private String  banner_url;
	private String trailer_url;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * @return the director
	 */
	public String getDirector() {
		return Director;
	}
	/**
	 * @param director the director to set
	 */
	public void setDirector(String director) {
		Director = director;
	}
	/**
	 * @return the banner_url
	 */
	public String getBanner_url() {
		return banner_url;
	}
	/**
	 * @param banner_url the banner_url to set
	 */
	public void setBanner_url(String banner_url) {
		this.banner_url = banner_url;
	}
	/**
	 * @return the trailer_url
	 */
	public String getTrailer_url() {
		return trailer_url;
	}
	/**
	 * @param trailer_url the trailer_url to set
	 */
	public void setTrailer_url(String trailer_url) {
		this.trailer_url = trailer_url;
	}
}
