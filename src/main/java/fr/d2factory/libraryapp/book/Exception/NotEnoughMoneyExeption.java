package fr.d2factory.libraryapp.book.Exception;

public class NotEnoughMoneyExeption extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6439619546128097035L;
	
	public NotEnoughMoneyExeption() {
		System.out.println("Not enough money in the wallet ");
	}

}
