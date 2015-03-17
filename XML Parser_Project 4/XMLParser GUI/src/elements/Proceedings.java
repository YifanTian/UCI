package elements;

//import titlecontents.TitleContents;

public class Proceedings  extends Element{
	
	private String key;
	private String mdate;
//	private TitleContents tc;
	
	/**
	 * @param key
	 * @param mdate
	 */
	public Proceedings(String key, String mdate) {
		this.key = key;
		this.mdate = mdate;
	}
	
//	/**
//	 * @return the tc
//	 */
//	public TitleContents getTc() {
//		return tc;
//	}
//	
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
