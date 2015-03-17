package connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.TextArea;
import titlecontents.TitleContents;
import elements.Article;
import elements.Book;
import elements.Element;
import elements.InCollection;
import elements.InProceedings;
import elements.MastersThesis;
import elements.PhdThesis;
import elements.Proceedings;
import elements.WWW;

public class QueryExecuter {

	private Connection conn;
	private HashMap<String,String> library;
	StringBuilder sb = new StringBuilder();
	private int count;
	public QueryExecuter () {
		try
		{
			conn = ConnectionManager.getSessionConnection();
			conn.setCatalog("dblp");
			conn.setAutoCommit(false);
		}
		catch (Exception e) {
			e.getStackTrace();
		}
		library = new HashMap<String, String>();
	}
	
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	private void setCount(int count) {
		this.count = count;
	}

	public void addArticle(Article article) {
		if(library.containsKey(article.getKey())) {
			sb.append("Found Duplicate Entry: " + article.getKey() + "\n");
			setCount(getCount() + 1);
			return;
		}
		library.put(article.getKey(), article.getKey());
		
		int id = insertDocument(article, "article");
		
		insertAuthors(id, article.getTc().getAuthors());		
	}
	public void addInProceedings(InProceedings inproceedings) {
		if(library.containsKey(inproceedings.getKey())) {
			sb.append("Found Duplicate Entry: " + inproceedings.getKey() + "\n");
			setCount(getCount() + 1);
			return;
		}
		library.put(inproceedings.getKey(), inproceedings.getKey());
		
		int id = insertDocument(inproceedings, "inproceedings");
		
		insertAuthors(id, inproceedings.getTc().getAuthors());
				
	}

	public void addProceedings(Proceedings proceedings) {
		if(library.containsKey(proceedings.getKey())) {
			sb.append("Found Duplicate Entry: " + proceedings.getKey() + "\n");
			setCount(getCount() + 1);
			return;
		}
		library.put(proceedings.getKey(), proceedings.getKey());
		
		int id = insertDocument(proceedings,"proceedings");
		
		insertAuthors(id, proceedings.getTc().getAuthors());
		
	}
	public void addBook(Book book) {
		if(library.containsKey(book.getKey())) {
			sb.append("Found Duplicate Entry: " + book.getKey() + "\n");
			setCount(getCount() + 1);
			return;
		}
		library.put(book.getKey(), book.getKey());
		
		int id = insertDocument(book, "book");
		
		insertAuthors(id, book.getTc().getAuthors());
	}
	public void addInCollection(InCollection incollection) {
		if(library.containsKey(incollection.getKey())) {
			sb.append("Found Duplicate Entry: " + incollection.getKey() + "\n");
			setCount(getCount() + 1);
			return;
		}
		library.put(incollection.getKey(), incollection.getKey());
		
		int id = insertDocument(incollection, "incollection");
		
		insertAuthors(id, incollection.getTc().getAuthors());		
	}
	public void addPhdThesis(PhdThesis phdthesis) {
		if(library.containsKey(phdthesis.getKey())) {
			sb.append("Found Duplicate Entry: " + phdthesis.getKey() + "\n");
			setCount(getCount() + 1);
			return;
		}
		library.put(phdthesis.getKey(), phdthesis.getKey());
		
		int id = insertDocument(phdthesis, "phdthesis");
		
		insertAuthors(id, phdthesis.getTc().getAuthors());		
	}
	public void addMastersThesis(MastersThesis mastersthesis) {
		if(library.containsKey(mastersthesis.getKey())) {
			sb.append("Found Duplicate Entry: " + mastersthesis.getKey() + "\n");
			setCount(getCount() + 1);
			return;
		}
		library.put(mastersthesis.getKey(), mastersthesis.getKey());
		
		int id = insertDocument(mastersthesis, "mastersthesis");
		
		insertAuthors(id, mastersthesis.getTc().getAuthors());		
	}
	public void addWWW(WWW www) {
		if(library.containsKey(www.getKey())) {
			sb.append("Found Duplicate Entry: " + www.getKey() + "\n");
			setCount(getCount() + 1);
			return;
		}
		library.put(www.getKey(), www.getKey());

		int id = insertDocument(www, "www");
		insertAuthors(id, www.getTc().getAuthors());		
	}

	public String commitTransaction() {
		try {
			conn.commit();
			conn.setAutoCommit(true);
		}
		catch (SQLException e) {
			e.getStackTrace();
		}
		return sb.toString();
		
	}
	
	private int insertDocument(Element element, String g) {
		int did = -1;
		String editor = element.getTc().getEditor();
		String bookTitle = element.getTc().getBooktitle();
		String genre = g;
		String publisher = element.getTc().getPublisher();
		int editorId = getEditor(editor);
		int bookTitleId = getBookTitle(bookTitle);
		int genreId = getGenre(genre);
		int publisherId = getPublisher(publisher);
		TitleContents ctc = element.getTc();
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO tbl_dblp_document VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, ctc.getTitle());
			if(ctc.getPages() == null || ctc.getPages().isEmpty()) {
				ps.setNull(2, java.sql.Types.NULL);
				ps.setNull(3, java.sql.Types.NULL);
			} else {
				try {
					ps.setInt(2, Integer.parseInt(ctc.getPages().substring(0, ctc.getPages().indexOf("-"))));
					ps.setInt(3, Integer.parseInt(ctc.getPages().substring(ctc.getPages().indexOf("-") + 1, ctc.getPages().length())));
				} catch (StringIndexOutOfBoundsException e) {
					sb.append("Invalid Page Tag Formatting Given: " + ctc.getPages() + "Setting to null\n");
					ps.setNull(2, java.sql.Types.NULL);
					ps.setNull(3, java.sql.Types.NULL);
				} catch (NumberFormatException e) {
					sb.append("Invalid Page Tag Formatting Given: " + ctc.getPages() + "Setting to null\n");
					ps.setNull(2, java.sql.Types.NULL);
					ps.setNull(3, java.sql.Types.NULL);
				}
			}
			if(ctc.getYear() == null || ctc.getYear().isEmpty()) {
				ps.setNull(4, java.sql.Types.NULL);
			} else {
				try {
					ps.setInt(4,  Integer.parseInt(ctc.getYear()));
				} catch (NumberFormatException e) {
					sb.append("Invalid Year: " + ctc.getVolume() + " Setting to NULL\n");
					ps.setNull(4, java.sql.Types.NULL);
				}
			}
			if(ctc.getVolume() == null || ctc.getVolume().isEmpty()) {
				ps.setNull(5, java.sql.Types.NULL);
			} else {
				try {
					ps.setInt(5, Integer.parseInt(ctc.getVolume()));
				} catch (NumberFormatException e) {
					sb.append("Invalid Volume: " + ctc.getVolume() + " Setting to NULL\n");
					ps.setNull(5, java.sql.Types.NULL);
				}
			}
			if(ctc.getNumber() == null || ctc.getNumber().isEmpty()) {
				ps.setNull(6, java.sql.Types.NULL);
			} else {
				try {
					ps.setInt(6, Integer.parseInt(ctc.getNumber()));
				} catch (NumberFormatException e) {
					sb.append("Invalid Number: " + ctc.getVolume() + " Setting to NULL\n");
					ps.setNull(6, java.sql.Types.NULL);
				}
			}
			ps.setString(7, ctc.getUrl());
			ps.setString(8, ctc.getEe());
			ps.setString(9, ctc.getCdrom());
			ps.setString(10, ctc.getCite());
			ps.setString(11, ctc.getCrossref());
			ps.setString(12, ctc.getIsbn());
			ps.setString(13, ctc.getSeries());
			if(editorId == java.sql.Types.NULL) {
				ps.setNull(14, editorId);
			} else {
				ps.setInt(14, editorId);
			}
			if(bookTitleId == java.sql.Types.NULL) {
				ps.setNull(15, bookTitleId);
			} else {
				ps.setInt(15, bookTitleId);
			}
			if(genreId == java.sql.Types.NULL) {
				ps.setNull(16, genreId);
			} else {
				ps.setInt(16, genreId);
			}
			if(publisherId == java.sql.Types.NULL) {
				ps.setNull(17, publisherId);
			} else {
				ps.setInt(17, publisherId);
			}
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return did;
	}

	private void insertAuthors(int id, ArrayList<String> authors) {
		
		if(id <= 0) {
			sb.append("Author/Document Insertion Failed\n");
			return;
		}
		
		PreparedStatement ps;
		for(String a : authors ) {			
			try {
				ps = conn.prepareStatement("INSERT INTO tbl_author_document_mapping VALUES(NULL, ?, ?)", Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1,id);
				ps.setInt(2, getEditor(a));
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private int getPublisher(String publisher) {
		
		if(publisher == null) {
			return java.sql.Types.NULL;
		}
		
		PreparedStatement ps;
		int id = -1;
		try {
			ps = conn.prepareStatement("SELECT id FROM tbl_publisher WHERE publisher_name = ?");
			ps.setString(1, publisher);
			ResultSet r = ps.executeQuery();
			if(r.isBeforeFirst()) {
				r.next();
				return r.getInt("id");
			} else {
				ps = conn.prepareStatement("INSERT INTO tbl_publisher VALUES(NULL, ?)", Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, publisher);
				ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				rs.next();
				return rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	private int getGenre(String genre) {
		
		if(genre == null) {
			return java.sql.Types.NULL;
		}
		
		PreparedStatement ps;
		int id = -1;
		try {
			ps = conn.prepareStatement("SELECT id FROM tbl_genres WHERE genre_name = ?");
			ps.setString(1, genre);
			ResultSet r = ps.executeQuery();
			if(r.isBeforeFirst()) {
				r.next();
				return r.getInt("id");
			} else {
				ps = conn.prepareStatement("INSERT INTO tbl_genres VALUES(NULL, ?)", Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, genre);
				ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				rs.next();
				return rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	private int getBookTitle(String bookTitle) {
		
		if(bookTitle == null) {
			return java.sql.Types.NULL;
		}
		
		PreparedStatement ps;
		int id = -1;
		try {
			ps = conn.prepareStatement("SELECT id FROM tbl_booktitle WHERE title = ?");
			ps.setString(1, bookTitle);
			ResultSet r = ps.executeQuery();
			if(r.isBeforeFirst()) {
				r.next();
				return r.getInt("id");
			} else {
				ps = conn.prepareStatement("INSERT INTO tbl_booktitle VALUES(NULL, ?)", Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, bookTitle);
				ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				rs.next();
				return rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	private int getEditor(String editor) {
		
		if(editor == null) {
			return java.sql.Types.NULL;
		}
		
		PreparedStatement ps;
		int id = -1;
		try {
			ps = conn.prepareStatement("SELECT id FROM tbl_people WHERE name = ?");
			ps.setString(1, editor);
			ResultSet r = ps.executeQuery();
			if(r.isBeforeFirst()) {
				r.next();
				return r.getInt("id");
			} else {
				ps = conn.prepareStatement("INSERT INTO tbl_people VALUES(NULL, ?)", Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, editor);
				ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				rs.next();
				return rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
}
