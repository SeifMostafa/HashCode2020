package model;
import java.util.ArrayList;

import Drive.BookScanningTask;

public 	class Library  {
	public ArrayList<Book> books = new ArrayList<Book>();
	public Integer shipPerDay, signUpProccess;
	public Integer scoreSumOfBooks = 0;
	public int idx;
	
	public Library(ArrayList<Book> bookIn,int shipPerDayTime,int signUpProccessTime,int index){
		books = bookIn;
		shipPerDay = shipPerDayTime;
		signUpProccess = signUpProccessTime;
		idx = index;
		for(Book b:books) {
			Book book = BookScanningTask.allBooks.get(b.idx);
			scoreSumOfBooks+=book.score;
			b.score = book.score;
			book.addLib(this);
		}
	}
	
	
	public ArrayList<Book> getBooks() {
		return books;
	}
	public void setBooks(ArrayList<Book> books) {
		this.books = books;
	}
	public int getShipPerDay() {
		return shipPerDay;
	}
	public void setShipPerDay(int shipPerDay) {
		this.shipPerDay = shipPerDay;
	}
	public int getSignUpProccess() {
		return signUpProccess;
	}
	public void setSignUpProccess(int signUpProccess) {
		this.signUpProccess = signUpProccess;
	}
	public int getScoreSumOfBooks() {
		return scoreSumOfBooks;
	}
	public void setScoreSumOfBooks(int scoreSumOfBooks) {
		this.scoreSumOfBooks = scoreSumOfBooks;
	}


	@Override
	protected Object clone() throws CloneNotSupportedException {
		Library cloned = (Library)super.clone();
		ArrayList<Book> clonedBooks  = new ArrayList<Book>();
		for(Book b:cloned.books) {
			clonedBooks.add((Book)b.clone());
		}
		cloned.setBooks(clonedBooks);
		return cloned;
	}


	@Override
	public String toString() {
		return "Lib[idx: "+idx+", sumScore: "+scoreSumOfBooks+", SignUp proccess time: "+signUpProccess+", ship per day: "+shipPerDay+"]";
	}

	
	
	
	
}