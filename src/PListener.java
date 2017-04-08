import java.io.*;
import java.net.*;

public class PListener extends Thread{
	Client client;
	BufferedReader in;
	PrintWriter out;
	Socket socket;
	boolean talking;
	public PListener(Socket socket, Client client, Boolean talking){
		this.socket = socket;
		this.client = client;
		this.talking = talking;
	}
	public void run(){
		try{
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			if(talking != true){
				out.println(client.clientIP);
				out.println(client.clientName);
				out.println(client.csPort);
			}
			String incoming;
			while(true){
				incoming = in.readLine();
				if(incoming.equals("CHAT CHAT")){
					String pHN = in.readLine();
					int pPN = Integer.parseInt(in.readLine());
					client.connectToPeer(pHN, pPN);
				}else if(incoming.equals("CHAT START")){
					client.acceptPeerConnection();
				}else if(incoming.equals("CHAT LEAVE")){
					client.printConsole("Chat ended");
					client.printConsole("Reconnecting to the server now...");
					client.connectToServer();
				}else{
					client.printConsole(incoming);
				}
			}
		} catch (Exception exception){
			client.printConsole("An error has occured.");
		}
	}
	public void sendMessage(String message){
		out.println(message);
	}

}
