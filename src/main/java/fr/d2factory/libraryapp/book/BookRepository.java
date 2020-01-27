package fr.d2factory.libraryapp.book;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * The book repository emulates a database via 2 HashMaps
 */
public final class BookRepository {
    public Map<ISBN, Book> availableBooks = new HashMap<>();
    public Map<Book, LocalDate> borrowedBooks = new HashMap<>();

    public void addBooks(List<Book> books){
    	
    	availableBooks = books.stream().collect(Collectors.toMap(Book::getIsbn, b-> b));
    }
    
    public void addBook(Book book) {

    	availableBooks.put(book.getIsbn(), book);
    }

    public Book findBook(long isbnCode) { 

    	 return availableBooks.get(new ISBN(isbnCode)); 
    }

    public boolean saveBookBorrow(Book book, LocalDate borrowedAt){
        
    	if(findBook(book.getIsbn().getIsbnCode()) != null) {
    		borrowedBooks.put(book, borrowedAt);
    		availableBooks.remove(book.getIsbn());
    		
    		return true;
    	}
    	
    	return false;
    }
    
    public Boolean returnBorrowedBook(Book book) {
    	if(borrowedBooks.get(book) != null) {
    		borrowedBooks.remove(book);
    		availableBooks.put(book.getIsbn(),book);
    		return true;
    	}
    	return false;
    }
   

    public LocalDate findBorrowedBookDate(Book book) {

    	return borrowedBooks.get(book);
    }
}
