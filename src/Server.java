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
	
}
