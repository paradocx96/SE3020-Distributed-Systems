package activem;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Receiver implements MessageListener {
	private ConnectionFactory factory = null;
	private Connection connection = null;
	private Session session = null;
	private Destination destination = null;
	private MessageConsumer consumer = null;

	public Receiver() {

	}

	public void receiveMessage() {
		try {
			// Getting JMS connection from the server
	        //default broker URL is : tcp://localhost:8161
			factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
			connection = factory.createConnection();
			connection = factory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("SAMPLEQUEUE");
	        //creating a message consumer to receive messages from a destination
			consumer = session.createConsumer(destination);
	        //calling the receiving method to receive a message and assign it to the variable called message
			Message message = consumer.receive();

	        //extracting the message content and cast to the appropriate message type.
			if (message instanceof TextMessage) {
				TextMessage text = (TextMessage) message;
				System.out.println("Message is : " + text.getText());
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	//getting a message from the JMS queue
	public static void main(String[] args) throws JMSException, InterruptedException {
		
	    //creating an object from receiver class and calling the receieveMessage method inside it
		Receiver receiver = new Receiver();

          // TO DO: Uncomment this to register as a listener
		 receiver.registerListener();
		
           // TO DO: Comment this line
//		receiver.receiveMessage();
		
		System.out.println("print");
		int i=0;
		for(;;) {
			Thread.sleep(1000);
			i++;
			System.out.print("\r" + i);

		}
	}
	
	private void registerListener() throws JMSException {
		
		factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
		connection = factory.createConnection();
		connection = factory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		destination = session.createQueue("SAMPLEQUEUE");
        //creating a message consumer to receive messages from a destination
		consumer = session.createConsumer(destination);
		
		//MessageConsumer consumer = session.createConsumer(destination);
		consumer.setMessageListener(this); 
		
	}
	
          // Async callback method
	    @Override
	    public void onMessage(Message message) {
		
	        if(message instanceof TextMessage) {
	          
	          try {
				System.out.println("Received " +  ((TextMessage) message).getText());
			} catch (JMSException e) {
				 
				e.printStackTrace();
			}
	        }
	        else
	        	System.out.println("Unexpected non-text message received.");
	    }
}