package fr.d2factory.libraryapp.member;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.ISBN;
import fr.d2factory.libraryapp.book.Exception.NotEnoughMoneyExeption;
import fr.d2factory.libraryapp.library.Library;

/**
 * A member is a person who can borrow and return books to a {@link Library}
 * A member can be either a student or a resident
 */
public abstract class Member {
    /**
     * An initial sum of money the member has
     */
	protected UUID id;
	protected String name;
    protected float wallet;
    protected boolean isLate;
    protected List<Book> membersBorrowedBooks;  
    protected int BorrowedBookNumber;
    /**
     * The member should pay their books when they are returned to the library
     *
     * @param numberOfDays the number of days they kept the book
     * @throws NotEnoughMoneyExeption 
     */
    public abstract float payBook(long numberOfDays) throws NotEnoughMoneyExeption;
    
    public Member(UUID id, String name, float wallet) {
    	this.id = id;
    	this.name = name;
    	this.wallet = wallet;
    	this.isLate = false;
    	this.membersBorrowedBooks = new ArrayList<Book>();
    	this.BorrowedBookNumber = 0;
    }
    
    public float getWallet() {
        return wallet;
    }

    public void setWallet(float wallet) {
        this.wallet = wallet;
    }

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isLate() {
		return isLate;
	}

	public void setLate(boolean isLate) {
		this.isLate = isLate;
	}
	public void addBook(Book book) {
		membersBorrowedBooks.add(book);
	}


	public List<Book> getMembersBorrowedBooks() {
		return membersBorrowedBooks;
	}


	public void setMembersBorrowedBooks(List<Book> membersBorrowedBooks) {
		this.membersBorrowedBooks = membersBorrowedBooks;
	}


	public int getBorrowedBookNumber() {
		return BorrowedBookNumber;
	}


	public void setBorrowedBookNumber(int borrowedBookNumber) {
		BorrowedBookNumber = borrowedBookNumber;
	}
	
    
}
