package fr.d2factory.libraryapp.member;

import java.util.List;
import java.util.UUID;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.Exception.NotEnoughMoneyExeption;

public class Student extends Member {

	private boolean firstYear = false;

	public Student(UUID id, String name, float wallet, boolean firstYear) {
		super(id, name, wallet);
		this.firstYear = firstYear;
	}

	@Override
	public float payBook(long numberOfDays) throws NotEnoughMoneyExeption {
		float toPay = 0;
		long paidDay = numberOfDays;

		if (firstYear && numberOfDays >= 15) {
			paidDay = numberOfDays - 15;
		} else if (!firstYear) {
			toPay = paidDay * 0.10f;

			if (numberOfDays > 30) {
				isLate = true;
			}
		}

		wallet = wallet - toPay;
		if (toPay > wallet) {
			throw new NotEnoughMoneyExeption();
		}
		return toPay;
	}

	public boolean isFirstYear() {
		return firstYear;
	}

	public void setFirstYear(boolean firstYear) {
		this.firstYear = firstYear;
	}

}
