package it.polito.library;

import java.util.HashSet;
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
    // collection of bookscopies map key=bookid value=bookcopy
	private TreeMap<String,Book> idsColl = new TreeMap<>();
	// readers collection map key=id value=reader obj
	private TreeMap<String,Reader> readersColl = new TreeMap<>();
	// rentals collection
	private Set<Rental> rentalsColll = new HashSet<>();
	
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
    	this.idsColl.put(id, b);
    	
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
    	
    	return this.idsColl.keySet();
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
       
    	// check if the title exists
    	if (! this.booksColl.containsKey(bookTitle))
    		throw new LibException();
    	
    	// get all the copies we have
    	LinkedList<Book> bookCopies = this.booksColl.get(bookTitle);
    	// and find the first one available
    	String book_id=null;
    	for (Book b : bookCopies) {
    		if (!b.isRented())
    			book_id = b.getCopy_id();
    			
    	}
    	
    	if (book_id == null)
    		return "Not available";
    	return book_id;
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
		
		// first thing to do check if the reader and the book are registered
		if (!this.readersColl.containsKey(readerID) || !this.idsColl.containsKey(bookID))
			throw new LibException();
		
		Reader reader = this.readersColl.get(readerID);
		Book book = this.idsColl.get(bookID);
		// check if this is duplicated rental
		for (Rental ren : this.rentalsColll) {
			if (ren.getReaderId().equals(readerID) && ren.getBookId().equals(bookID)) {
				// we overwrite the older rental
				ren.setStartDate(startingDate);
				return;
			}
		}
		
		// check if either of them is currently involved in a rental
		if (reader.isRenting() || book.isRented()) {
			throw new LibException();
		}
		
		// create a new rental object
		Rental r = new Rental(bookID,readerID,startingDate);
		// update the boolean flags for reader and book involved in a rental
		book.setRented(true);reader.setRenting(true);
		
		// adding it to our collection 
		this.rentalsColll.add(r);
		
		
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
    	
    	// check if the reader and the book are registered
		if (!this.readersColl.containsKey(readerID) || !this.idsColl.containsKey(bookID))
			throw new LibException();
    	
    	Book b = this.idsColl.get(bookID);
		Reader reader = this.readersColl.get(readerID);
    	
		// check if the book is currently on rental
		if (!b.isRented())
			throw new LibException();
		
    	// looking for the rental object
		for (Rental ren : this.rentalsColll) {
			if (ren.getReaderId().equals(readerID) && ren.getBookId().equals(bookID)) {
				// adding the ending date to the rental object
				ren.setEndDate(endingDate);
				// update the list of book's rentals
				b.addRental(ren);
				// update the reader's and book's flags since the rent is done
				b.setRented(false);reader.setRenting(false);
				// remove it from the set of rentals
				this.rentalsColll.remove(ren);
				break;
			}
		}
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
        Book b = this.idsColl.get(bookID);
        
        SortedMap<String, String> res = b.getRentalsList().stream().collect(Collectors.toMap(
        		Rental::getReaderId, 
        		r->{
        			return r.getStartDate()+" "+r.getEndDate();
        		},
        		(existingValue, newValue) -> existingValue + " " + newValue, // in case of duplicated keys we merge the two values
                TreeMap::new)
        		);
    	return res;
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
