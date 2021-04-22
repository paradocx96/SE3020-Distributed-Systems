import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class XsltTransform {
    public static void main(String[] args) throws IOException,
                                                  URISyntaxException,
                                                  TransformerException {
        Source             xslt        = new StreamSource(new File(args[0]));
        Source             text        = new StreamSource(new File(args[1]));
        TransformerFactory factory     = TransformerFactory.newInstance();
        Transformer        transformer = factory.newTransformer(xslt);
        
        transformer.transform(text, new StreamResult(new File(args[2])));
    }
}