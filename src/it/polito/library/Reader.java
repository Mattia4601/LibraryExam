package it.polito.library;

public class Reader {
	private String readerId;
	private String name;
	private String surname;
	private boolean renting=false;
	
	public boolean isRenting() {
		return this.renting;
	}
	
	public void setRenting(boolean val) {
		this.renting=val;
	}
	public String getReaderId() {
		return readerId;
	}
	public void setReaderId(String readerId) {
		this.readerId = readerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public Reader(String readerId, String name, String surname) {
		super();
		this.readerId = readerId;
		this.name = name;
		this.surname = surname;
	}
	
}
