import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
//import java.lang.SecurityManager;
import java.rmi.RemoteException;

public class MathClient {
	public static void main(String[] args) {
		// if (System.getSecurityManager() == null)
		// {
		// System.setSecurityManager (new RMISecurityManager());
		// }

		System.setProperty("java.security.policy", "file:allowall.policy");

		MathService service = null;
		try {
			service = (MathService) Naming.lookup("//localhost/CalculatorService");

			System.out.println("Client count : " + service.clientCount());
			System.out.println("Add : " + service.add(2, 2));
			System.out.println("Subtract : " + service.subtract(5, 2));
			System.out.println("Multiply : " + service.multiply(2, 6));
			System.out.println("Divide : " + service.divide(4, 2));

		} catch (NotBoundException ex) {
			System.err.println(ex.getMessage());
		} catch (MalformedURLException ex) {
			System.err.println(ex.getMessage());
		} catch (RemoteException ex) {
			System.err.println(ex.getMessage());
		}
	}
}
