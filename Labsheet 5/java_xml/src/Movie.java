public class Movie {	
	private String name;
	private int runtime;
	private String directors;
	private int released; 
	private String cast;	
	public Movie(){}	

	public Movie(String name, int runtime, String directors,int released, String cast) {		
		this.name = name;
		this.runtime = runtime;
		this.directors = directors;
		this.released = released;
		this.cast = cast;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRuntime() {
		return runtime;
	}
	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}
	public String getDirectors() {
		return directors;
	}
	public void setDirectors(String directors) {
		this.directors = directors;
	}
	public int getReleased() {
		return released;
	}
	public void setReleased(int released) {
		this.released = released;
	}
	public String getCast() {
		return cast;
	}
	public void setCast(String cast) {
		this.cast = cast;
	}

}