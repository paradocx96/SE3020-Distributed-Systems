package bookservice;

import java.util.ArrayList;
import java.util.List;

public class Books {

	private static ArrayList<Book> books = new ArrayList<>();

	static {
		books.add(new Book(1, "The Outliers", 500));
		books.add(new Book(2, "World Is Flat", 550));
	}

	public static List<Book> getBooks() {
		return books;
	}
}