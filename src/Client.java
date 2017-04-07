

import java.io.*;
import java.net.*;

public class Client {
   
	String serverName;
	Socket connection;
	int serverPort;
	ServerSocket csSocket;
	String clientIP;
	String clientName;
	int csPort;
	String peerName;
	int peerPort;
	Socket peerSocket;
	
	CListener cListener;
	PListener pListener;
	
	BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	
	public Client(String serverName, int serverPort){
		this.serverName = serverName;
		this.serverPort = serverPort;
		setUpServer();
		connectToServer();
		
		try{
			cListener = new CListener(this, input);
			cListener.start();
			while(true){	
			}
		} catch (Exception e){
			
		}
	}
	public void connectToServer(){
		try{
			connection = new Socket(serverName, serverPort);
			pListener = new PListener(connection, this, false);
			pListener.start();
			System.out.println("You are now connected to the Server");
		}catch (Exception e){
			System.out.println("You were unable to connect to the server. Some error occured");
		}
	}
	public void setUpServer(){
		try{
			csSocket = new ServerSocket(0);
			clientIP = Inet4Address.getLocalHost().getHostAddress();
			clientName = Inet4Address.getLocalHost().getHostAddress();
			csPort = csSocket.getLocalPort();
		} catch (Exception e){
			
		}
	}
	public void acceptPeerConnection(){
		try{
			peerSocket = csSocket.accept();
			pListener = new PListener(peerSocket, this, true);
			pListener.start();
			System.out.println("The connection has now been established.");
		}catch(IOException ioException){
			
		}
	}
	public void connectToPeer(String peerName, int peerPort){
		System.out.println("Connecting...");
		try{
			this.peerName = peerName;
			this.peerPort = peerPort;
			Socket peerSocket = new Socket(this.peerName, this.peerPort);
			pListener = new PListener(peerSocket, this, true);
			pListener.start();
			System.out.println("Connected.");
		} catch (Exception exception){
			System.out.println("Failed");
		}
	}
	public void printConsole(String message){
		System.out.println(message);
	}
	public void sendMessage(String message){
		System.out.println(message);
	}
}
