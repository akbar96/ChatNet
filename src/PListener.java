import java.io.*;
import java.net.*;

public class PListener extends Thread{
	Client client;
	BufferedReader reader;
	PrintWriter writer;
	Socket socket;
	boolean talking;
	public PListener(Socket socket, Client client, Boolean talking){
		this.socket = socket;
		this.client = client;
		this.talking = talking;
	}
	public void run(){
		try{
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);
			if(talking != true){
				writer.println(client.clientIP);
				writer.println(client.clientName);
				writer.println(client.csPort);
			}
			String incoming;
			while(true){
				incoming = reader.readLine();
				if(incoming.equals("ChatNet.Chat")){
					String pHN = reader.readLine();
					int pPN = Integer.parseInt(reader.readLine());
					client.connectToPeer(pHN, pPN);
				}else if(incoming.equals("ChatNet.Start")){
					client.acceptPeerConnection();
				}else if(incoming.equals("ChatNet.Leave")){
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
		writer.println(message);
	}

}
