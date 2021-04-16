package activem;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Sender {

	//creating a connection factory object which client uses to create a connection to a provider. 
	private ConnectionFactory factory = null;
	//initializing the connection with JMS provider. 
	private Connection connection = null;
	// initializing a session to producing and consuming messages.
	private Session session = null;
	//initializing a destination to specify the target of the message 
	private Destination destination = null;
	//initializing a message producer for sending messages to a destination.
	private MessageProducer producer = null;

	public Sender() {

	}

	public void sendMessage() {

		try {
			factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
			
	        //creating a connection using factory object
			
			connection = factory.createConnection();
			
			// TO DO: Set the async mode to true
			((ActiveMQConnection)connection).setUseAsyncSend(true);
	        
			
			//start the connection,causing message delivery to begin.
			connection.start();
			/* creating a session. 
	         * first argument means the session is not transacted. The second argument means the session 
	         * automatically acknowledges messages when they have been received successfully.
	         */
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			//creating the messaging queue called SAMPLEQUEUE
			destination = session.createQueue("SAMPLEQUEUE");
	        //creating a producer for a destination  
			producer = session.createProducer(destination);
	        //creating the message using a session
			TextMessage message = session.createTextMessage();
			message.setText("Hello ...This is a sample message..sending from FirstClient");
		    //sending the message
			producer.send(message);
			System.out.println("Sent: " + message.getText());

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	//sends a message to JMS queue
	public static void main(String[] args) {
		
	    //creating an object from sender class and calling the sendMessage method inside it.
		Sender sender = new Sender();
		sender.sendMessage();
	}

}