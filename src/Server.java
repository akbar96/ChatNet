/*
 * This program has been made by Akbar Khan for EECS 3214 Winter 2017
 * The purpose of this program is to handle connections from clients
 * at a server and provide the clients IP address and listening port numbers
 * of the peers to establish TCP connections with other peers and enabling
 * them to chat with each other.
 * Certain components of the program have been adopted from thenewboston tutorials.
 * */



import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server extends JFrame{
	
	private JTextField userText; 	//The field where the user can input the text
	private JTextArea chatWindow;	//The JFram window where conversations among the clients and servers
									// can be read.
	private ObjectOutputStream output; // Messages going out from the user
	private ObjectInputStream input;	//Messages being received by the user
	private ServerSocket server;	//The server
	private Socket connection; //Contains connections via sockets
	
	//Constructs the GUI for Server
	public Server(){
		super("ChatNet");	//Naming the service as ChatNet
		userText = new JTextField();	//Initiates a chat feild
		userText.setEditable(false);	//Set the boolean for chatbox to false. This is the case initially, 
										//this prevents the user form typing when they are not connected to
										//anyone
		userText.addActionListener(		//Adding an action listener
				new ActionListener(){	//As soon as a new action is detected
					public void actionPerformed(ActionEvent event){ /*This method is enabled and it passes
					 												this action as event to the sendMessage
					 												method*/
						sendMessage(event.getActionCommand());	//passing in the text typed in to the text field using.getActionCommand
						userText.setText("");//This sets the user text field to nothing after the message has been sent.
						
					}
					
				}
		);
		add(userText, BorderLayout.NORTH); //Adding the field in the window
		chatWindow = new JTextArea();	//creation of Main window with all messages displayed in this window
		add(new JScrollPane(chatWindow));//The main window gets added to the screen using this command
		setSize(400,300);	//Set the size of the window
		setVisible(true);	//Sets the window to be visible
		
				
		
	}
	
	static String[] playersList = new String[1000]; //Initializes the array to the capacity of a 1000 elements
	static int i=0;	//Populates the array
	static String people; //Concatenated string which is filled up with the contents of the array
    public void startRunning() throws IOException {

//        if (args.length != 1) {
//            System.err.println("Usage: java EchoServer <port number>");
//            System.exit(1);
//        }
//        No longer need this, the port number will always be 21910
        int portNumber = 21910;
        
        /*
         * As soon as we establish a new socket, we move on to putting the system in a constant search for
         * potential connections. The while true loop runs for ever and as long as the server is alive, it
         * will keep looking for clients wanting to connect
         */
        
        try {
        	
            ServerSocket server = new ServerSocket(portNumber, 100); //The port number is set to 21910 and at
            													// one point of time only 100 people can wait
            													// at the port
            showMessage("Waiting for someone to connect... \n");
            while(true){ 										//infinite loop which always checks for connections
            	try{
            		Socket connection = server.accept();		//The connection gets accepted here
            		new ClientThread(connection).start();		/* Once the connection is accepted, the server starts a thread
            													by calling the class CLient Thread which caters to new 
            													connections and gives them an id.*/
            	}
            	catch (EOFException eofException){
            		showMessage("\n Server ended the connection! "); //Looks for errors
            	}finally{
            		closeConnection();
            	}
            }
            
        } catch (Exception e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
	
}
