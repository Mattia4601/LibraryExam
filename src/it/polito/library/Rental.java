package it.polito.library;

public class Rental {
	
	private String readerId;
	private String bookId;
	private String startDate;
	private String endDate;
	
	public String getRentalPeriod() {
		return this.startDate+" "+this.endDate;
	}
	
	// getters and setters
	public String getReaderId() {
		return readerId;
	}
	public void setReaderId(String readerId) {
		this.readerId = readerId;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	// constructor
	public Rental(String bookId, String readerId, String startDate) {
		super();
		this.readerId = readerId;
		this.bookId = bookId;
		this.startDate = startDate;
	}
	
	
	
	
}
