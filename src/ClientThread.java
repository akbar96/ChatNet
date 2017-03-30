
import java.io.*;
import java.net.*;


public class ClientThread extends Thread {
	
	Socket connection;				 //Variable for the socket and portNumber
	String id;							/*Each new client is assigned an id of their choice and it is stored locally for the client
	 									This helps in maintaining the list when the client wants to leave*/
	Server server;
	boolean joined = false;			/*This helps establish whether or not the client has already joined the server and limits
		 								the same client from joining under multiple id's without leaving*/
	boolean full = false; 				/*This is the chat room. In this case we only deal with one chat 
										room that may consist of only 2 players at a time */
	
	public ClientThread(Socket socket, Server server){
		this.connection =  socket;
		this.server = server;
	}
	
	public void run(){
		System.out.println("Connected to a new Client");
		while(true){  //This allows us to continuously listen on the socket for any new requests from the client.
		try{
			
		 PrintWriter out = new PrintWriter(connection.getOutputStream(), true);  //Writes an output to the socket
	     BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));//Reads any input from the socket
	     							//out.println("\n Streams are now setup! \n");			Not to be used in this version       	
	     							//    This is removed for now since the GUI implementation isn't the one we are proceeding with     ableToType(true);	//Allows the input fields to be editable 
	     String inputLine;	//Contains the messages received by the user to the server.
         
         while ((inputLine = in.readLine()) != null) { //ensures that there some input to compute
         	
        	 if (inputLine.equals("JOIN")){ //Checks if the client wishes to join
         		
        		 if(joined == false){ //Makes sure that the same client is not joining twice
	         		
        			out.println("Enter your numeric ID: "); //Asks client for an id to address them with. This id must be numerical
	         		
        			Server.playersList[Server.i] = in.readLine();//id is served in the array of players
	         		
        			out.println("Your id is: " + Server.playersList[Server.i]);//ID is confirmed to the player
	         		
        			id = Server.playersList[Server.i];//stored in the variable id
	         		
        			Server.i = Server.i + 1;//This statement increments the counter and sets it up for the next client
	         		
        			joined = true;//Once the client has joins, the boolean joined is set to true so that they cannot join again
         		
        		 }else{
         			
        			 out.println("You have already joined"); //If the player tries to join without leaving, they are reminded that they are already connected
         		}
         	
        	 }else if(inputLine.equals("LIST")){				//Checks if the command is list
         		
         		//out.println("Message received: LIST");
         		String message = "";						//Initializes the string message as empty
         		
         		for(int a = 0; a < 1000; a++){
         			
         			if(Server.playersList[a]!= null){		//Avoids null entries from being printed
         				//System.out.println(Server.playersList[a]);
         				message = message.concat(Server.playersList[a]);//Gets the list of players
         				message = message.concat(", ");
         			}
         		}
         		out.println(message);			//List of live players is printed
         		
         	}else if(inputLine.equals("LEAVE")){//Checks if the player wants to leave
         		
         		for(int a = 0; a < 1000; a++){
         				
         			if(Server.playersList[a] == id){//Looks for the player's id in the array playersList
         					Server.playersList[a] = null;//Once found, that element is set to null
         					joined = false;				//The player's joined boolean is changed back to false allowing them join agian
         					
         				break;//Stops from marking other players with the same id as null/left
         				}
         				
         			}
         		
         		out.println("Closing Connections...");	//Displays the message that the connections are being closed.
         		out.println("You are no longer connected to Server\nStreams closed.\nConnection closed\nGoodbye!");
         		//Same reason as above 	ableToType(false);						//Takes away the ability to type from the user
         		try{
         			out.close();						//Closes the output stream
         			in.close();							//Closes the input stream
         			connection.close();					//Closes the socket
         			System.out.println("Client has Left");//Lets the player know they have left
         			break;
         		}catch(IOException ioException){
         			
         		}
         		
         	}else if(inputLine.equals("CHAT")){
         		if(Server.playersList == null){
         			out.println("There is no one connected to the Server. Try again later when someone else is connected.");
         		}
         		else if(Server.playersList != null && full == false){
         			out.println("The chat engine should be executed now...");
         		}
         		
         	}else{
         		
         		out.println("Command Invalid. Please try again");//Addresses all other commands besides JION, LIST, and LEAVE
         	}
         }
//         private void sendMessage(String message){		Not certain if this is the method to be used
//        	 try{
//        		 
//        	 }catch(IOException ioException){
//        		 
//        	 }
//         }
        
		}catch(Exception e){//LOoks for exception
        	 
         }
		}
	
	}

	
}

