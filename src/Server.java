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
import java.util.ArrayList;
//import java.awt.*;				Not to be used in this version
//import java.awt.event.*;			Not to be used in this version
//import javax.swing.*;				Not to be used in this version
//
public class Server{		//Extends JFrame has been removed since it is Not to be used in this version
	
			//	private JTextField userText; 	//The field where the user can input the text
			//	private JTextArea chatWindow;	//The JFram window where conversations among the clients and servers
												// can be read.
			//	private ObjectOutputStream output; // Messages going out from the user		Not useful in this program.
			//	private ObjectInputStream input;	//Messages being received by the user	A different io stream is used
			//	private ServerSocket server;	//The server
			//	private Socket connection; //Contains connections via sockets
				
			//	//Constructs the GUI for Server
			//	public Server(){
			//		super("ChatNet");	//Naming the service as ChatNet
			//		userText = new JTextField();	//Initiates a chat feild
			//		userText.setEditable(false);	//Set the boolean for chatbox to false. This is the case initially, 
			//										//this prevents the user form typing when they are not connected to
			//										//anyone
			//		userText.addActionListener(		//Adding an action listener
			//				new ActionListener(){	//As soon as a new action is detected
			//					public void actionPerformed(ActionEvent event){ /*This method is enabled and it passes
			//					 												this action as event to the sendMessage
			//					 												method*/
			//						sendMessage(event.getActionCommand());	//passing in the text typed in to the text field using.getActionCommand
			//						userText.setText("");//This sets the user text field to nothing after the message has been sent.
			//						
			//					}
			//					
			//				}
			//		);
			//		add(userText, BorderLayout.NORTH); //Adding the field in the window
			//		chatWindow = new JTextArea();	//creation of Main window with all messages displayed in this window
			//		add(new JScrollPane(chatWindow));//The main window gets added to the screen using this command
			//		setSize(400,300);	//Set the size of the window
			//		setVisible(true);	//Sets the window to be visible
			////		
			//				
			//		
//	}
	
	String serverName;
	int portNumber;
	public ArrayList<User> clients = new ArrayList<User>();
	public ArrayList<ClientThread> activeThrd = new ArrayList<ClientThread>(); 
						//static String[] playersList = new String[1000]; //Initializes the array to the capacity of a 1000 elements
						//static int i=0;	//Populates the array
						//static String people; //Concatenated string which is filled up with the contents of the array
   ServerSocket serverSocket;
   Socket clientSocket;
   
	public static void main(String[] args) throws IOException {

						//        if (args.length != 1) {
						//            System.err.println("Usage: java EchoServer <port number>");
						//            System.exit(1);
						//        }
						//        No longer need this, the port number will always be 21910

        Server server = new Server("Host", 21910);
    }
				        /*
				         * As soon as we establish a new socket, we move on to putting the system in a constant search for
				         * potential connections. The while true loop runs for ever and as long as the server is alive, it
				         * will keep looking for clients wanting to connect
				         */
     public Server(String serverName, int portNumber){   
    	 
    	this.serverName = serverName;
    	this.portNumber = portNumber;
    	
        try {
        	
            serverSocket = new ServerSocket(portNumber); 						//The port number is set to 21910 and at
				            													// one point of time only 100 people can wait
				            													// at the port

            System.out.println("Waiting for someone to connect... \n");
            while(true){ 										//infinite loop which always checks for connections
            	
                clientSocket = serverSocket.accept();
            	ClientThread newThread = new ClientThread(clientSocket, this);
                newThread.start();
                activeThrd.add(newThread);
            	
            }
        }catch(IOException ioexception){
        	ioexception.printStackTrace();
        }
     }
     
     public User getUserByName(String name){
    	 for(int i = 0; i<activeThrd.size(); i++){
    		 if(activeThrd.get(i).user.username.equals(name)){
    			 return activeThrd.get(i).user;
    		 }
    	 }
    	 return null;
     }
     
     public ClientThread getServerThreadByUserName(String name){
    	 for(int i = 0; i<activeThrd.size(); i++){
    		 if(activeThrd.get(i).user.username.equals(name)){
    			 return activeThrd.get(i);
    		 }
    	 }
    	 return null;
     }
     
     public boolean nameExists(String username){
    	 for(int i=0;i<activeThrd.size();i++){
    		 if(activeThrd.get(i).user.username.equals(username)){
    			 return true;
    		 }
    	 }
    	 return false;
     }
     
     public void removeByName(String username){
    	 for(int i = 0; i< activeThrd.size();i++){
    		 if(activeThrd.get(i).user.username.equals(username)){
    			 activeThrd.remove(i);
    			 return;
    		 }
    	 }
     }
}