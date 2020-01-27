package fr.d2factory.libraryapp.member;

import java.util.List;
import java.util.UUID;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.Exception.NotEnoughMoneyExeption;

public class Resident extends Member {

	public Resident(UUID id, String name, float wallet) {
		super(id, name, wallet);
	}

	@Override
	public float payBook(long numberOfDays) throws NotEnoughMoneyExeption {
		float toPay = (numberOfDays >= 60 ? 60 : numberOfDays) * 0.10f;
		if (numberOfDays > 60) {
			isLate = true;
			toPay += ((numberOfDays - 60) * 0.20f);
		}

		wallet -= toPay;

		return toPay;
	}

}
