import java.beans.XMLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class SerializeToXML {	

	private static final String SERIALIZED_FILE_NAME="dvd.xml";

	public static void main(String args[]){

		Movie bourneIndentity=new Movie("The Bourne Identity",119,"Doug Liman",2002,"Matt Damon, Franka Potente");
		Movie bourneSupermacy=new Movie("The Bourne Supremacy",108,"Paul Greengrass",2004,"Matt Damon, Franka Potente, Joan Allen");
		Movie bourneUltimatum=new Movie("The Bourne Ultimatum",115,"Paul Greengrass",2007,"Matt Damon, Edgar Ramirez, Joan Allen");
		Movie bourneLegacy=new Movie("The Bourne Legacy",135,"Tony Gilroy",2012,"Jeremy Renner, Rachel Weisz, Edward Norton");

		List moviesList=new ArrayList();
		moviesList.add(bourneIndentity);
		moviesList.add(bourneSupermacy);
		moviesList.add(bourneUltimatum);
		moviesList.add(bourneLegacy);

		DVD bourneSeries=new DVD();
		bourneSeries.setMovies(moviesList);

		XMLEncoder encoder=null;
		try{
		encoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream(SERIALIZED_FILE_NAME)));
		}catch(FileNotFoundException fileNotFound){
			System.out.println("ERROR: While Creating or Opening the File dvd.xml");
		}
		encoder.writeObject(bourneSeries);
		encoder.close();

	}

}