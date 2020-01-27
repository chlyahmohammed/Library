package fr.d2factory.libraryapp.book.Exception;

/**
 * This exception is thrown when a member who owns late books tries to borrow another book
 */
public class HasLateBooksException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -937734736079898291L;
	
	public HasLateBooksException() {
		System.out.println("This member owns late books: Can not borrow another book before returning the already borrowed books");
	}
}
