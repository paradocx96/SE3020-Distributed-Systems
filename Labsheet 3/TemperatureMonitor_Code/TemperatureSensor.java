interface TemperatureSensor extends java.rmi.Remote
{
	public double getTemperature() throws
		java.rmi.RemoteException;
	public void addTemperatureListener
		(TemperatureListener listener )
		throws java.rmi.RemoteException;
	public void removeTemperatureListener
		(TemperatureListener listener )
		throws java.rmi.RemoteException;
}
