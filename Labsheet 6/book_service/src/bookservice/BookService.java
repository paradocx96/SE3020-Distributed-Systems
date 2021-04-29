package bookservice;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

@Path("/books")
public class BookService {

	List<Book> books;
	Books bk = new Books();

	public BookService() {
		books = Books.getBooks();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Book> getBooks() {
		return books;
	}

	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Book getBook(@PathParam("id") int id) {
		for (Book b : books) {
			if (b.getId() == id)
				return b;
		}
		// book with the given id is not found, so throw 404 error
		throw new NotFoundException();
	}

	@Path("{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Book delteBook(@PathParam("id") int id) {
		for (Book b : books) {
			if (b.getId() == id) {
				books.remove(b);
				return b;
			}
		}
		// book with the given id is not found, so throw 404 error
		throw new NotFoundException();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List addBook(@Context HttpHeaders httpHeaders, Book book) {

		books.add(book);
		return books;

	}
}