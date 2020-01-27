package fr.d2factory.libraryapp.library;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.book.Exception.BookNotAvailableException;
import fr.d2factory.libraryapp.book.Exception.HasLateBooksException;
import fr.d2factory.libraryapp.book.Exception.NotEnoughMoneyExeption;
import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.member.Resident;
import fr.d2factory.libraryapp.member.Student;
import junit.framework.TestCase;
import tools.BooksTools;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Do not forget to consult the README.md :)
 */

public class LibraryTest extends TestCase {
    private LibraryDefaultService libraryDefaultService ;
    private BookRepository bookRepository;
    private List<Book> books;


    @BeforeEach
    void setup() throws JsonParseException, JsonMappingException, IOException {
    	bookRepository = new BookRepository();
    	books = BooksTools.readBooksFromJSON();
    	libraryDefaultService = new LibraryDefaultService();
    	
    	bookRepository.addBooks(books);
    	libraryDefaultService.setBookRepository(bookRepository);
    }
    
    @Test
    void test_read_books(){
    	int EXPECTED_BOOKS = 4;
    	assertEquals(books.size(), EXPECTED_BOOKS);
    }
    
    @Test
    void member_can_borrow_a_book_if_book_is_available() throws HasLateBooksException, NotEnoughMoneyExeption, BookNotAvailableException {
    	Member member1  = new Student(UUID.randomUUID(), "sichlyah", 100, true);
    	Book book = libraryDefaultService.borrowBook(8523, member1, LocalDate.now());
    	assertNotNull(book);
    }

    @Test
    void member_can_borrow_a_book_if_he_dosent_have_enought_money() throws HasLateBooksException, NotEnoughMoneyExeption, BookNotAvailableException{
    	Member member6 = new Resident(UUID.randomUUID(), "Rachelle", 0);
    	Assertions.assertThrows(NotEnoughMoneyExeption.class, () -> libraryDefaultService.borrowBook(8523, member6, LocalDate.now()));
    }
    @Test
    void borrowed_book_is_no_longer_available() throws HasLateBooksException, NotEnoughMoneyExeption, BookNotAvailableException{
    	Member member6 = new Resident(UUID.randomUUID(), "Ghita", 100);
    	libraryDefaultService.borrowBook(8523, member6, LocalDate.now().minusDays(5));
    	Assertions.assertThrows(BookNotAvailableException.class, () -> libraryDefaultService.borrowBook(8523, member6, LocalDate.now()));
    }

    @Test
    void residents_are_taxed_10cents_for_each_day_they_keep_a_book() throws HasLateBooksException, NotEnoughMoneyExeption, BookNotAvailableException{
    	Book book = libraryDefaultService.getBookRepository().findBook(9658);
    	Member member2 = new Resident(UUID.randomUUID(), "Edouard", 100);
    	
    	libraryDefaultService.borrowBook(9658, member2, LocalDate.now().minusDays(2));
    	float totalPaid = libraryDefaultService.returnBook(book, member2);
    	assertEquals(totalPaid, 0.2f);
    }

    @Test
    void students_pay_10_cents_the_first_30days() throws HasLateBooksException, NotEnoughMoneyExeption, BookNotAvailableException{
    	Book book = libraryDefaultService.getBookRepository().findBook(9658);
    	Member member2 = new Student(UUID.randomUUID(),"Ulrik",100,false);
    	
    	libraryDefaultService.borrowBook(9658, member2, LocalDate.now().minusDays(30));
    	float totalPaid = libraryDefaultService.returnBook(book, member2);
    	assertEquals(3.0f, totalPaid);
    }

    @Test
    void students_in_1st_year_are_not_taxed_for_the_first_15days() throws HasLateBooksException, NotEnoughMoneyExeption, BookNotAvailableException{
    	Book book = libraryDefaultService.getBookRepository().findBook(9658);
    	Member member3 = new Student(UUID.randomUUID(), "Angelina", 100, true);
    	
    	// borrow the book for 10 days
    	libraryDefaultService.borrowBook(9658, member3, LocalDate.now().minusDays(10));
    	float totalPaid = libraryDefaultService.returnBook(book, member3);

    	assertEquals(0.0f, totalPaid);
    }
    
    @Test
    void residents_pay_20cents_for_each_day_they_keep_a_book_after_the_initial_60days() throws HasLateBooksException, NotEnoughMoneyExeption, BookNotAvailableException{
    	Book book = libraryDefaultService.getBookRepository().findBook(9658);
    	Member member4 = new Resident(UUID.randomUUID(), "Louise", 100);
    	
    	// borrow the book for 80days 
    	libraryDefaultService.borrowBook(9658, member4, LocalDate.now().minusDays(80));
    	float totalPaid = libraryDefaultService.returnBook(book, member4);
    	assertEquals(10f, totalPaid);
    }
  
    @Test
    void members_cannot_borrow_book_if_they_have_late_books() throws Exception {
    	
    	Member member4 = new Resident(UUID.randomUUID(), "Audré", 100);

		libraryDefaultService.borrowBook(9658, member4, LocalDate.now().minusDays(80));
		Assertions.assertThrows(HasLateBooksException.class, () -> libraryDefaultService.borrowBook(4125, member4, LocalDate.now()));
    }
}
