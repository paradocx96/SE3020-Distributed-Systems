import java.beans.XMLDecoder;
import java.io.*;

public class DeserializeFromXML {	
	private static final String SERIALIZED_FILE_NAME="dvd.xml";

	public static void main(String[] args) {
		XMLDecoder decoder=null;
		try {
			decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(SERIALIZED_FILE_NAME)));
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File dvd.xml not found");
		}
		DVD bourneSeries=(DVD)decoder.readObject();
		System.out.println(bourneSeries);

	}
}