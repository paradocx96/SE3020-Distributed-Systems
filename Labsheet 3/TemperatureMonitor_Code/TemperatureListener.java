interface TemperatureListener extends java.rmi.Remote
{
	public void temperatureChanged(double temperature) throws 	java.rmi.RemoteException;
}
