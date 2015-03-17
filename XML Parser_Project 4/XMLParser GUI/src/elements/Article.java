package elements;

//import titlecontents.TitleContents;

public class Article extends Element{

	private String key;
    private String  reviewid;
    private String rating;
    private String mdate;
//    private TitleContents tc;
    
	/**
	 * @param key
	 * @param reviewid
	 * @param rating
	 * @param mdate
	 */
	public Article(String key, String reviewid, String rating, String mdate) {
		this.key = key;
		this.reviewid = reviewid;
		this.rating = rating;
		this.mdate = mdate;
	}

//	
//	/**
//	 * @return the tc
//	 */
//	public TitleContents getTc() {
//		return tc;
//	}
//	/**
//	 * @param tc the tc to set
//	 */
//	public void setTc(TitleContents tc) {
//		this.tc = tc;
//	}
	
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	
	/**
	 * @return the reviewid
	 */
	public String getReviewid() {
		return reviewid;
	}
	
	/**
	 * @param reviewid the reviewid to set
	 */
	public void setReviewid(String reviewid) {
		this.reviewid = reviewid;
	}
	
	/**
	 * @return the rating
	 */
	public String getRating() {
		return rating;
	}
	
	/**
	 * @param rating the rating to set
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}
	
	/**
	 * @return the mdate
	 */
	public String getMdate() {
		return mdate;
	}
	
	/**
	 * @param mdate the mdate to set
	 */
	public void setMdate(String mdate) {
		this.mdate = mdate;
	}

}
