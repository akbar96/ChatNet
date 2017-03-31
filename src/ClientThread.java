
import java.io.*;
import java.net.*;


public class ClientThread extends Thread {
	
	Socket connection;				 //Variable for the socket and portNumber
	Server server;
	public boolean joined = false;			/*This helps establish whether or not the client has already joined the server and limits
		 								the same client from joining under multiple id's without leaving*/
	public String name;
	public String ip;
	public String hostname;
	public int port;
	public User user = new User();
	

	public ClientThread(Socket socket, Server server){
		
		this.connection =  socket;
		this.server = server;
	}
	
	public void run(){
		
		
		//System.out.println("Connected to a new Client");
		//while(true){  //This allows us to continuously listen on the socket for any new requests from the client.
		
		
		
		
		try{
			
		 PrintWriter out = new PrintWriter(connection.getOutputStream(), true);  //Writes an output to the socket
	     BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));//Reads any input from the socket
	     											//out.println("\n Streams are now setup! \n");			Not to be used in this version       	
	     
	     user.ipAddress = in.readLine();
	     user.hostname = in.readLine();
	     user.portNumber = Integer.parseInt(in.readLine());
	     
	     String inputLine;							//Contains the messages received by the user to the server.
         
	    while(true){ 
	     
	    
	         while ((inputLine = in.readLine()) != null) { 		//ensures that there some input to compute
	         	
	        	 if (inputLine.toUpperCase().equals("JOIN")){				 //Checks if the client wishes to join
	         		
	        		 if(joined == false){ 								//Makes sure that the same client is not joining twice
	        			out.println("Enter your name: "); 		//Asks client for an id to address them with. This id must be numerical
	        			String name = in.readLine();
	        			out.println("Your id is: " + user.username);//ID is confirmed to the player
		         		
	        			joined = true;									//Once the client has joins, the boolean joined is set to true so that they cannot join again
	         		
	        		 }else{
	         			
	        			 out.println("You have already joined"); 		//If the player tries to join without leaving, they are reminded that they are already connected
	         		}
	         	
	        	 }else if(inputLine.equals("LIST")){					//Checks if the command is list
	         		
	         		//out.println("Message received: LIST");
	         		int numThreads = 0;								//Initialized the number of threads to 0
	         		
	         		for(int a = 0; a < server.activeThrd.size(); a++){
	         			
	         			if(server.activeThrd.get(a).joined == TRUE){				//Avoids null entries from being printed
	         				message = message.concat(Server.playersList[a]);//Gets the list of players
	         				message = message.concat(", ");
	         			}
	         		}
	         		out.println(message);								//List of live players is printed
	         		
	         	}else if(inputLine.equals("LEAVE")){					//Checks if the player wants to leave
	         		
	         		for(int a = 0; a < 1000; a++){
	         				
	         			if(Server.playersList[a] == id){				//Looks for the player's id in the array playersList
	         					Server.playersList[a] = null;			//Once found, that element is set to null
	         					joined = false;							//The player's joined boolean is changed back to false allowing them join agian
	         					
	         				break;										//Stops from marking other players with the same id as null/left
	         				}
	         				
	         			}
	         		
	         		out.println("Closing Connections...");				//Displays the message that the connections are being closed.
	         		out.println("You are no longer connected to Server \nStreams closed. \nConnection closed \nGoodbye!");
	         		
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
         }
        
		}catch(Exception e){//LOoks for exception
        	 
         }
		}
	
	}

	
//}

