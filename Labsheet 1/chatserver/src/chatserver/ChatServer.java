package chatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;

/**
 * A multithreaded chat room server. When a client connects the server requests
 * a screen name by sending the client the text "SUBMITNAME", and keeps
 * requesting a name until a unique one is received. After a client submits a
 * unique name, the server acknowledges with "NAMEACCEPTED". Then all messages
 * from that client will be broadcast to all other clients that have submitted a
 * unique screen name. The broadcast messages are prefixed with "MESSAGE ".
 *
 * Because this is just a teaching example to illustrate a simple chat server,
 * there are a few features that have been left out. Two are very useful and
 * belong in production code:
 *
 * 1. The protocol should be enhanced so that the client can send clean
 * disconnect messages to the server.
 *
 * 2. The server should do some logging.
 */
public class ChatServer {

	/**
	 * The port that the server listens on.
	 */
	private static final int PORT = 9001;

	/**
	 * The set of all names of clients in the chat room. Maintained so that we can
	 * check that new clients are not registering name already in use.
	 */
	private static HashSet<String> names = new HashSet<String>();

	/**
	 * The set of all the print writers for all the clients. This set is kept so we
	 * can easily broadcast messages.
	 */
	private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();
	
	/* HashSet variable for selected client */
	private static HashSet<String> selectedtClientNames = new HashSet<String>();	

	/* Fore store client names and PrintWriter */
	private static HashMap<String, PrintWriter> clientNameWithPrintWritter = new HashMap<String, PrintWriter>();

	/**
	 * The appplication main method, which just listens on a port and spawns handler
	 * threads.
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("The chat server is running.");
		ServerSocket listener = new ServerSocket(PORT);
		try {
			while (true) {
				
				Socket socket = listener.accept();

				Thread handlerThread = new Thread(new Handler(socket));
				handlerThread.start();
				System.out.println("New client connected!");
			}
		} finally {
			listener.close();
		}
	}

	/**
	 * A handler thread class. Handlers are spawned from the listening loop and are
	 * responsible for a dealing with a single client and broadcasting its messages.
	 */
	private static class Handler implements Runnable {
		private String name;
		private Socket socket;
		private BufferedReader in;
		private PrintWriter out;

		/**
		 * Constructs a handler thread, squirreling away the socket. All the interesting
		 * work is done in the run method.
		 */
		public Handler(Socket socket) {
			this.socket = socket;
		}

		/**
		 * Services this thread's client by repeatedly requesting a screen name until a
		 * unique one has been submitted, then acknowledges the name and registers the
		 * output stream for the client in a global set, then repeatedly gets inputs and
		 * broadcasts them.
		 */
		public void run() {
			try {

				// Create character streams for the socket.
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);

				// Request a name from this client. Keep requesting until
				// a name is submitted that is not already used. Note that
				// checking for the existence of a name and adding the name
				// must be done while locking the set of names.
				while (true) {
					out.println("SUBMITNAME");
					name = in.readLine();
					if (name == null) {
						return;
					}

					// TODO: Add code to ensure the thread safety of the
					// the shared variable 'names'

					synchronized (names) {
						if (!names.contains(name)) {
							names.add(name);
							break;
						}
					}
				}

				// Now that a successful name has been chosen, add the
				// socket's print writer to the set of all writers so
				// this client can receive broadcast messages.
				out.println("NAMEACCEPTED");
				writers.add(out);

				// TODO: You may have to add some code here to broadcast all clients the new
				// client's name for the task 9 on the lab sheet.
				
				/* adding client name to hashMap */ 
				clientNameWithPrintWritter.put(name, out);
				
				/* When client added to the server, client's name send to existing client's list.
				 * And existing clients name to new client's list. */
				for (String key : clientNameWithPrintWritter.keySet()) {

					if (key.equals(name)) {
						for (String name : names) {
							clientNameWithPrintWritter.get(key).println("CLIENTNAME" + name);
						}
					} else {
						clientNameWithPrintWritter.get(key).println("NAME" + name);
					}
				}

				// Accept messages from this client and broadcast them.
				// Ignore other clients that cannot be broadcasted to.
				
				// TODO: Add code to send a message to a specific client and not
				// all clients. You may have to use a HashMap to store the sockets along
				// with the chat client names
				while (true) {

					String input = in.readLine();
					
					/* Check if there any message with 'SELECTEDMESSAGE'.
					 * Then send message to selected client. */
					if (input.startsWith("SELECTEDMESSAGE")) {
						if (input == null) {
							return;
						}

						for (String count : selectedtClientNames) {
							for (String key : clientNameWithPrintWritter.keySet()) {
								if (count.equals(key)) {
									clientNameWithPrintWritter.get(key).println("MESSAGE " + name + ": " + input.substring(15));
								}
							}
						}

						/* clear selectedtClientNames for accept new. */
						selectedtClientNames.clear();

					/* If get message with 'CHECK'. Then send message to all clients */
					} else if (input.startsWith("CHECK")) {

						for (PrintWriter writer : writers) {
							writer.println("MESSAGE " + name + ": " + input.substring(5));
						}
					} else {
						selectedtClientNames.add(input);
					}
				}

			} // TODO: Handle the SocketException here to handle a client closing the socket
			catch (java.net.SocketException e) {
				System.out.println("Seems one client left.");

			} catch (IOException e) {
				System.out.println(e);
			} finally {
				// This client is going down! Remove its name and its print
				// writer from the sets, and close its socket.
				if (name != null) {
					names.remove(name);

					/* When client left the server, remove client from the HashMap. */
					clientNameWithPrintWritter.remove(name);

					if (out != null) {
						writers.remove(out);
					}

					try {
						socket.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}
}