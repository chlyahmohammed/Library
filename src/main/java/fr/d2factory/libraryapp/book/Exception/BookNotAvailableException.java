package fr.d2factory.libraryapp.book.Exception;

public class BookNotAvailableException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8680798755736518199L;

	public BookNotAvailableException() {
		System.out.println("This book can not be borrowed");
	}
}
