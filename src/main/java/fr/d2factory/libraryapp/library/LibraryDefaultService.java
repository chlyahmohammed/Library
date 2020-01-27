package fr.d2factory.libraryapp.library;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.book.Exception.BookNotAvailableException;
import fr.d2factory.libraryapp.book.Exception.HasLateBooksException;
import fr.d2factory.libraryapp.book.Exception.NotEnoughMoneyExeption;
import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.member.Resident;
import fr.d2factory.libraryapp.member.Student;


public class LibraryDefaultService implements Library {
	private BookRepository bookRepository;

	public BookRepository getBookRepository() {
		return bookRepository;
	}

	public void setBookRepository(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public void updateSituation(Member member) {
		long lateOffset = (member instanceof Resident) ? 60 : 30;

		for (Book book : member.getMembersBorrowedBooks()) {
			bookRepository.findBorrowedBookDate(book);
			long daysDiff = ChronoUnit.DAYS.between(bookRepository.findBorrowedBookDate(book), LocalDate.now());

			if (daysDiff > lateOffset) {
				member.setLate(true);
				return;
			}
		}

		member.setLate(false);
	}

	@Override
	public Book borrowBook(long isbnCode, Member member, LocalDate borrowedAt)
			throws HasLateBooksException, NotEnoughMoneyExeption, BookNotAvailableException {

		updateSituation(member);

		Book book;
		if (member.isLate()) {
			throw new HasLateBooksException();
		} else {
			book = bookRepository.findBook(isbnCode);

			if (book != null) {
				if ((member instanceof Resident && member.getBorrowedBookNumber() < 2) || member instanceof Student) {
					if (member.getWallet() <= 0) {
						throw new NotEnoughMoneyExeption();
					}

					member.addBook(book);
					bookRepository.saveBookBorrow(book, borrowedAt);

				}

			} else {
				throw new BookNotAvailableException();
			}

			return book;
		}
	}

	@Override
	public float returnBook(Book book, Member member) throws NotEnoughMoneyExeption {
		float totalPaid = 0;

		if (member.getMembersBorrowedBooks().contains(book)) {

			if (member.isLate())
				member.setLate(false);

			long daysDiff = ChronoUnit.DAYS.between(bookRepository.findBorrowedBookDate(book), LocalDate.now());

			totalPaid = member.payBook(daysDiff);

			member.setBorrowedBookNumber(member.getBorrowedBookNumber() - 1);
			bookRepository.returnBorrowedBook(book);

			return totalPaid;
		}

		return totalPaid;
	}

}
