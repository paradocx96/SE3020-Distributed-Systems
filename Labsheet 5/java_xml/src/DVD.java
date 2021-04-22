import java.util.*;

public class DVD {
	private List movies = new ArrayList();

	public DVD() {
	}

	public List getMovies() {
		return movies;
	}

	public void setMovies(List movies) {
		this.movies = movies;
	}

	public String toString() {
		String movies = "";
		Movie movie = null;
		for (Object object : getMovies()) {
			movie = (Movie) object;
			// to do: modify the following line to print the movie year as well separated by a space after the name
			movies += movie.getName() + " " + movie.getReleased();
		}
		return movies;
	}
}