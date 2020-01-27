package fr.d2factory.libraryapp.library;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.Exception.BookNotAvailableException;
import fr.d2factory.libraryapp.book.Exception.HasLateBooksException;
import fr.d2factory.libraryapp.book.Exception.NotEnoughMoneyExeption;
import fr.d2factory.libraryapp.member.Member;

import java.time.LocalDate;

/**
 * The library class is in charge of stocking the books and managing the return delays and members
 *
 * The books are available via the {@link fr.d2factory.libraryapp.book.BookRepository}
 */
public interface Library {

    /**
     * A member is borrowing a book from our library.
     *
     * @param isbnCode the isbn code of the book
     * @param member the member who is borrowing the book
     * @param borrowedAt the date when the book was borrowed
     *
     * @return the book the member wishes to obtain if found
     * @throws HasLateBooksException in case the member has books that are late
     * @throws NotEnoughMoneyExeption 
     * @throws BookNotAvailableException 
     *
     * @see fr.d2factory.libraryapp.book.ISBN
     * @see Member
     */
    Book borrowBook(long isbnCode, Member member, LocalDate borrowedAt) throws HasLateBooksException, NotEnoughMoneyExeption, BookNotAvailableException;

    /**
     * A member returns a book to the library.
     * We should calculate the tarif and probably charge the member
     *
     * @param book the {@link Book} they return
     * @param member the {@link Member} who is returning the book
     * @throws NotEnoughMoneyExeption 
     *
     * @see Member#payBook(int)
     */
    float returnBook(Book book, Member member) throws NotEnoughMoneyExeption;
}
