package it.polito.library;

import java.util.LinkedList;

public class Book {
	
	private String copy_id;
	private String title;
	private boolean rented=false;
	private LinkedList<Rental> rentals = new LinkedList<>();
	
	// this method return the total numbers of rentals
	public int getRentalsNumber() {
		return this.rentals.size();
	}
	// this method tells us if this book has ever been rented
	public boolean hasBeenRented() {
		if (this.rentals.size()!=0 || this.rented == true)
			return true;
		return false;
	}
	public void addRental(Rental r) {
		this.rentals.add(r);
	}
	
	public Book(String title, String id) {
		super();
		this.title=title;
		this.copy_id=id;
	}
	
	public LinkedList<Rental> getRentalsList(){
		return this.rentals;
	}
	public boolean isRented() {
		return this.rented;
	}
	
	public void setRented(boolean val) {
		this.rented=val;
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
