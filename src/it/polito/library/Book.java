package it.polito.library;

public class Book {
	
	private String copy_id;
	private String title;
	
	public Book(String title, String id) {
		super();
		this.title=title;
		this.copy_id=id;
	}
	
	
	public void setCopyId(String id) {
		this.copy_id=id;
	}


	public String getCopy_id() {
		return copy_id;
	}


	public String getTitle() {
		return title;
	}
	

}
