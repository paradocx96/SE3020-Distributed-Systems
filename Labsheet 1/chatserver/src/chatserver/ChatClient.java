package chatserver;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;

/**
 * A simple Swing-based client for the chat server. Graphically it is a frame
 * with a text field for entering messages and a textarea to see the whole
 * dialog.
 *
 * The client follows the Chat Protocol which is as follows. When the server
 * sends "SUBMITNAME" the client replies with the desired screen name. The
 * server will keep sending "SUBMITNAME" requests as long as the client submits
 * screen names that are already in use. When the server sends a line beginning
 * with "NAMEACCEPTED" the client is now allowed to start sending the server
 * arbitrary strings to be broadcast to all chatters connected to the server.
 * When the server sends a line beginning with "MESSAGE " then all characters
 * following this string should be displayed in its message area.
 */
public class ChatClient {

	BufferedReader in;
	PrintWriter out;
	JFrame frame = new JFrame("Chatter");
	JTextField textField = new JTextField(40);
	JTextArea messageArea = new JTextArea(8, 40);

	// TODO: Add a list box
	/* Add checkbox for Broadcast feature */
	JCheckBox checkBox = new JCheckBox("Broadcast");
	
	/* Add list to show client list */
	JList userlist = new JList();
	
	/* For store clients */
	HashSet<String> selectedClient = new HashSet<String>();
	DefaultListModel listModel;
	
	/* For checkbox handle */
	static boolean checkBoxcheck = true;	
	

	/**
	 * Constructs the client by laying out the GUI and registering a listener with
	 * the textfield so that pressing Return in the listener sends the textfield
	 * contents to the server. Note however that the textfield is initially NOT
	 * editable, and only becomes editable AFTER the client receives the
	 * NAMEACCEPTED message from the server.
	 */
	public ChatClient() {

		// Layout GUI
		textField.setEditable(false);
		messageArea.setEditable(false);

		/* change GUI for new features */
		frame.getContentPane().add(textField, BorderLayout.NORTH);
		frame.getContentPane().add(checkBox, BorderLayout.WEST);
		frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.SOUTH);
		frame.getContentPane().add(new JScrollPane(userlist), BorderLayout.CENTER);
		
		/* checkbox checked */
		checkBox.setSelected(true);

		frame.pack();

		// TODO: You may have to edit this event handler to handle point to point
		// messaging,
		// where one client can send a message to a specific client. You can add some
		// header to
		// the message to identify the recipient. You can get the receipient name from
		// the listbox.
		textField.addActionListener(new ActionListener() {
			/**
			 * Responds to pressing the enter key in the textfield by sending the contents
			 * of the text field to the server. Then clear the text area in preparation for
			 * the next message.
			 */
			public void actionPerformed(ActionEvent e) {
				
				/* check whether is there any selected client in the list.
				 * then store in 'selectedClient'.
				 * then read hashset and send selected client list to server. */
				if (checkBoxcheck || userlist.isSelectionEmpty()) {
					out.println("CHECK" + textField.getText());
					textField.setText("");

				} else {
					ListModel listModel = userlist.getModel();
					
					for (int count : userlist.getSelectedIndices()) {
						selectedClient.add(listModel.getElementAt(count).toString());
					}
										
					for (String selectedList : selectedClient) {
						out.println(selectedList);
					}

					/* If it there selected client with massage, send them to server with message. */
					out.println("SELECTEDMESSAGE" + textField.getText());
					
					/* clear the text field. */
					textField.setText("");
					
					/* unSelect the client list*/
					userlist.clearSelection();
					selectedClient.clear();
				}
			}
		});

		/* Adding new listener to handle checkBox */
		checkBox.addActionListener(new ActionListener() {
			
			/* if the checkBox select then set the value of checkBoxcheck variable to TRUE */			
			@Override
			public void actionPerformed(ActionEvent e) {

				if (checkBox.isSelected()) {
					checkBoxcheck = true;
				} else {
					checkBoxcheck = false;
				}
			}
		});
	}

	/**
	 * Prompt for and return the address of the server.
	 */
	private String getServerAddress() {
		return JOptionPane.showInputDialog(frame, "Enter IP Address of the Server:", "Welcome to the Chatter", JOptionPane.QUESTION_MESSAGE);
	}

	/**
	 * Prompt for and return the desired screen name.
	 */
	private String getName() {
		return JOptionPane.showInputDialog(frame, "Choose a screen name:", "Screen name selection", JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * Connects to the server then enters the processing loop.
	 */
	private void run() throws IOException {

		// Make connection and initialize streams
		String serverAddress = getServerAddress();
		Socket socket = new Socket(serverAddress, 9001);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);

		// Process all messages from server, according to the protocol.
		// TODO: You may have to extend this protocol to achieve task 9 in the lab sheet
		listModel = new DefaultListModel();
		
		while (true) {
			
			String line = in.readLine();			
			if (line.startsWith("SUBMITNAME")) {				
				out.println(getName());
			} else if (line.startsWith("NAMEACCEPTED")) {
				textField.setEditable(true);
				
				
			/* Getting new client from the server and store them in listModel(DefalutListModel).
			 * then add them to listView */
			} else if (line.startsWith("NAME")) {
				listModel.addElement(line.substring(4));
				userlist.setModel(listModel);

			/* Getting all client from the server and store them in listModel(DefalutListModel).
			 * then add them to listView */
			} else if (line.startsWith("CLIENTNAME")) {
				listModel.addElement(line.substring(10));
				userlist.setModel(listModel);

			/* If client remove from the server then remove client from listModel(DefalutListModel).
			 * then update the listView */
			} else if (line.startsWith("REMOVECLIENT")) {
				listModel.addElement(listModel.indexOf(line.substring(12)));
				userlist.setModel(listModel);
				
			} else if (line.startsWith("MESSAGE")) {
				messageArea.append(line.substring(8) + "\n");
			}
		}
	}

	/**
	 * Runs the client as an application with a closeable frame.
	 */
	public static void main(String[] args) throws Exception {
		ChatClient client = new ChatClient();
		client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.frame.setVisible(true);
		client.run();
	}
}