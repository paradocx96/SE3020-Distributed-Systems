import java.rmi.*;
import java.rmi.server.*;
import java.net.*;

public class TemperatureMonitor extends UnicastRemoteObject implements
		TemperatureListener, Runnable {

	private int count = 0;

	public TemperatureMonitor() throws RemoteException {
	}

	public static void main(String[] args) {

	   System.setProperty("java.security.policy", "file:allowall.policy");
 

		try {
			String registration = "//localhost/TemperatureSensor";

			Remote remoteService = Naming.lookup(registration);
			TemperatureSensor sensor = (TemperatureSensor) remoteService;
			double reading = sensor.getTemperature();
			System.out.println("Original temp : " + reading);
			TemperatureMonitor monitor = new TemperatureMonitor();

			// TO DO: Add method call to register the listener in the server object
			
			// registering monitors
			sensor.addTemperatureListener(monitor);
			monitor.run();
			
		} catch (MalformedURLException mue) {
		} catch (RemoteException re) {
		} catch (NotBoundException nbe) {
		}
	}

	public void temperatureChanged(double temperature)
			throws java.rmi.RemoteException {
		System.out.println("\nTemperature change event : " + temperature);
		count = 0;
	}

	public void run() {
		for (;;) {
			count++;

		// note that this might only work on windows console
			System.out.print("\r" + count);
			try {
				Thread.sleep(100);
			} catch (InterruptedException ie) {
			}

		}

	}
}