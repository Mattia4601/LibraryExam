package it.polito.library;

import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;


public class LibraryManager {
	    
	// collection for each book and its copies: map key copyId value Book obj
	private TreeMap<String,LinkedList<Book>> booksColl = new TreeMap<>();
    // collection to associate each title to its number of copies, map key title, value int num_copies
	private TreeSet<String> idsColl = new TreeSet<>();
	// readers collection map key=id value=reader obj
	private TreeMap<String,Reader> readersColl = new TreeMap<>();
	
	
	// R1: Readers and Books 
    
    /**
	 * adds a book to the library archive
	 * The method can be invoked multiple times.
	 * If a book with the same title is already present,
	 * it increases the number of copies available for the book
	 * 
	 * @param title the title of the added book
	 * @return the ID of the book added 
	 */
    public String addBook(String title) {
        // this will be the next id
    	int size =1000 + this.idsColl.size();
    	
    	// create a new book
    	String id = String.valueOf(size);
    	Book b = new Book(title,id);
    	
    	// add the new id to the ids collection
    	this.idsColl.add(id);
    	
    	// check if the title of this book has already been inserted in our collection
    	if (this.booksColl.containsKey(title)) {
    		// if so, then add this new copy to the list of its copies
    		this.booksColl.get(title).add(b);
    	}
    	else {
    		// otherwise we must add the title and its first copy
    		LinkedList<Book> copiesColl = new LinkedList<>();
    		copiesColl.add(b);
    		this.booksColl.put(title, copiesColl);
    	}
    	// return the book's id
    	return id;
    }
    
    /**
	 * Returns the book titles available in the library
	 * sorted alphabetically, each one linked to the
	 * number of copies available for that title.
	 * 
	 * @return a map of the titles liked to the number of available copies
	 */
    public SortedMap<String, Integer> getTitles() {    	
        
    	SortedMap<String, Integer> res = new TreeMap<>();
    	this.booksColl.forEach((key, val) -> res.put(key, val.size()));
    	return res;
    }
    
    /**
	 * Returns the books available in the library
	 * 
	 * @return a set of the titles liked to the number of available copies
	 */
    public Set<String> getBooks() {    	    	
    	
    	return this.idsColl;
    }
    
    /**
	 * Adds a new reader
	 * 
	 * @param name first name of the reader
	 * @param surname last name of the reader
	 */
    public void addReader(String name, String surname) {
    	
    	int int_id = 1000 + this.readersColl.size();
    	String id = String.valueOf(int_id);
    	
    	// create the new reader 
    	Reader r = new Reader(id,name,surname);
    	
    	// adding it to the collection
    	this.readersColl.put(id, r);
    	
    }
    
    
    /**
	 * Returns the reader name associated to a unique reader ID
	 * 
	 * @param readerID the unique reader ID
	 * @return the reader name
	 * @throws LibException if the readerID is not present in the archive
	 */
    public String getReaderName(String readerID) throws LibException {
        
    	// check if the id exists
    	if (! this.readersColl.containsKey(readerID)) {
    		throw new LibException();
    	}
    	
    	// get the reader obj
    	Reader r = this.readersColl.get(readerID);
    	
    	String out = r.getName()+" "+r.getSurname();
    	
    	return out;
    }    
    
    
    // R2: Rentals Management
    
    
    /**
	 * Retrieves the bookID of a copy of a book if available
	 * 
	 * @param bookTitle the title of the book
	 * @return the unique book ID of a copy of the book or the message "Not available"
	 * @throws LibException  an exception if the book is not present in the archive
	 */
    public String getAvailableBook(String bookTitle) throws LibException {
        return null;
    }   

    /**
	 * Starts a rental of a specific book copy for a specific reader
	 * 
	 * @param bookID the unique book ID of the book copy
	 * @param readerID the unique reader ID of the reader
	 * @param startingDate the starting date of the rental
	 * @throws LibException  an exception if the book copy or the reader are not present in the archive,
	 * if the reader is already renting a book, or if the book copy is already rented
	 */
	public void startRental(String bookID, String readerID, String startingDate) throws LibException {
    }
    
	/**
	 * Ends a rental of a specific book copy for a specific reader
	 * 
	 * @param bookID the unique book ID of the book copy
	 * @param readerID the unique reader ID of the reader
	 * @param endingDate the ending date of the rental
	 * @throws LibException  an exception if the book copy or the reader are not present in the archive,
	 * if the reader is not renting a book, or if the book copy is not rented
	 */
    public void endRental(String bookID, String readerID, String endingDate) throws LibException {
    }
    
    
   /**
	* Retrieves the list of readers that rented a specific book.
	* It takes a unique book ID as input, and returns the readers' reader IDs and the starting and ending dates of each rental
	* 
	* @param bookID the unique book ID of the book copy
	* @return the map linking reader IDs with rentals starting and ending dates
	* @throws LibException  an exception if the book copy or the reader are not present in the archive,
	* if the reader is not renting a book, or if the book copy is not rented
	*/
    public SortedMap<String, String> getRentals(String bookID) throws LibException {
        return null;
    }
    
    
    // R3: Book Donations
    
    /**
	* Collects books donated to the library.
	* 
	* @param donatedTitles It takes in input book titles in the format "First title,Second title"
	*/
    public void receiveDonation(String donatedTitles) {
    }
    
    // R4: Archive Management

    /**
	* Retrieves all the active rentals.
	* 
	* @return the map linking reader IDs with their active rentals

	*/
    public Map<String, String> getOngoingRentals() {
        return null;
    }
    
    /**
	* Removes from the archives all book copies, independently of the title, that were never rented.
	* 
	*/
    public void removeBooks() {
    }
    	
    // R5: Stats
    
    /**
	* Finds the reader with the highest number of rentals
	* and returns their unique ID.
	* 
	* @return the uniqueID of the reader with the highest number of rentals
	*/
    public String findBookWorm() {
        return null;
    }
    
    /**
	* Returns the total number of rentals by title. 
	* 
	* @return the map linking a title with the number of rentals
	*/
    public Map<String,Integer> rentalCounts() {
        return null;
    }

}
