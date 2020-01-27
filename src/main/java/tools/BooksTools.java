package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import fr.d2factory.libraryapp.book.Book;

public class BooksTools {
	
	public static String DEFAULT_LOCATION = "src/test/resources/books.json";

	public static List<Book> readBooksFromJSON() throws FileNotFoundException {
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new FileReader(DEFAULT_LOCATION));
		Book[] data = gson.fromJson(reader, Book[].class); 
		
		return Arrays.asList(data);
	}
	
	public static void main(String[] argv) throws JsonParseException, JsonMappingException, IOException {
		System.out.println(readBooksFromJSON().size());
	}
}
