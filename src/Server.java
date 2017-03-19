/*
 * This program has been made by Akbar Khan for EECS 3214 Winter 2017
 * The purpose of this program is to handle connections from clients
 * at a server and provide the clients IP address and listening port numbers
 * of the peers to establish TCP connections with other peers and enabling
 * them to chat with each other.
 * Certian components of the program have been adopted from thenewboston tutorials
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
	
	static String[] playersList = new String[1000]; //Initializes the array to the capacity of a 1000 elements
	static int i=0;	//Populates the array
	static String people; //Concatenated string which is filled up with the contents of the array
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }
        
        int portNumber = Integer.parseInt(args[0]);
        /*
         * As soon as we establish a new socket, we move on to putting the system in a constant search for
         * potential connections. The while true loop runs for ever and as long as the server is alive, it
         * will keep looking for clients wanting to connect
         */
        try {
        	
            ServerSocket server = new ServerSocket(Integer.parseInt(args[0]));
            
            while(true){ //infinite loop
            	try{
            		Socket connection = server.accept(); //The connection gets accepted here
            		new ClientThread(connection).start();		/* Once the connection is accepted, the server starts a thread
            													by calling the class CLient Thread which caters to new 
            													connections and gives them an id.*/
            	}
            	catch (Exception e){
            		System.out.println("An error occurred"); //Looks for errors
            	}
            }
            
        } catch (Exception e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
	
}
