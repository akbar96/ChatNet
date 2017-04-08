import java.io.BufferedReader;

public class CListener extends Thread{
	Client client;
	BufferedReader in;
	public CListener(Client client, BufferedReader in){
		this.in= in;
		this.client = client;
	}
	public void run(){
		while(true){
			try{
				String message;
				message = in.readLine();
				if(message.equals("CHAT LEAVE")){
					client.sendMessage(message);
					client.printConsole("Chat ended");
					client.printConsole("Reconnecting to the server now...");
					client.connectToServer();
				}else{
					client.sendMessage(message);
				}
			}catch (Exception exception){
				client.printConsole("Console error has occured");
			}
		}
	}
}
