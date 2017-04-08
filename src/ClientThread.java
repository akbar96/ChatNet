
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
	PrintWriter out;
	BufferedReader in;

	public ClientThread(Socket socket, Server server){
		
		this.connection =  socket;
		this.server = server;
	}
	
	public void run(){
		
		
		//System.out.println("Connected to a new Client");
		//while(true){  //This allows us to continuously listen on the socket for any new requests from the client.
		
		
		
		
		try{
			
			 out = new PrintWriter(connection.getOutputStream(), true);  //Writes an output to the socket
		     in = new BufferedReader(new InputStreamReader(connection.getInputStream()));//Reads any input from the socket
		     //out.println("\n Streams are now setup! \n");			//Not to be used in this version       	
		     
		     user.ipAddress = in.readLine();
		     user.hostname = in.readLine();
		     user.portNumber = Integer.parseInt(in.readLine());
		     
		     String inputLine;							//Contains the messages received by the user to the server.
			//System.out.println("Connected to a new Client");

		    while(true){ 
		     
				//System.out.println("Connected to a new Client");

		         while ((inputLine = in.readLine()) != null) { 		//ensures that there some input to compute
		     		//System.out.println(inputLine);
		     		//out.println(inputLine);
		        	 System.out.println("Incoming Request -> " + inputLine);
	
		        	 if (inputLine.toUpperCase().equals("JOIN")){				 //Checks if the client wishes to join
		         		
		        		 if(joined == false){ 								//Makes sure that the same client is not joining twice
		        			out.println("Enter your name: "); 		//Asks client for an id to address them with. This id must be numerical
		        			String name = in.readLine();
			         		if(!(server.nameExists(name))){
			         			user.username = name;
			         			joined = true;						//Once the client has joins, the boolean joined is set to true so that they cannot join again
			         			out.println("Welcome to ChatNet "+ user.username);
			         		}else{
			         			out.println("The name you chose is already in use. Please choose another one");
			         		}
		        		 }else{
		         			
		        			 out.println("You have already joined"); 		//If the player tries to join without leaving, they are reminded that they are already connected
		         		}
		         	
		        	 }else if(inputLine.toUpperCase().equals("LIST")){					//Checks if the command is list
		         		
		         		//out.println("Message received: LIST");
		         		int numThreads = 0;								//Initialized the number of threads to 0
		         		
		         		for(int a = 0; a < server.activeThrd.size(); a++){
		         			
		         			if(server.activeThrd.get(a).joined == true){				//Avoids null entries from being printed
		         				out.println((a+1) + ". " + server.activeThrd.get(a).user.username);
		         				numThreads++;
		         			}
		         		}
		         		
		         		if(numThreads==0){out.println("No active users at this moment");}
		         		
		         	}else if(inputLine.toUpperCase().equals("LEAVE")){					//Checks if the player wants to leave
		         		
		         		if(joined){
		         			
		         			
		         			server.removeByName(name);
		         			joined = false;
		         			user.username = "";
		         			out.flush();
		         			out.println("You have now left");
		         		
		         		
			         		out.println("Closing Connections...");				//Displays the message that the connections are being closed.
			         		// out.println("You are no longer connected to Server \nStreams closed. \nConnection closed \nGoodbye!");
			         		
			         		try{
			         			out.close();						//Closes the output stream
			         			in.close();							//Closes the input stream
			         			connection.close();					//Closes the socket
			         			System.out.println("Client has Left");//Lets the player know they have left
			         			break;
			         			
			         			
			         		}catch(IOException ioException){
			         		
			         		}	
		         		}
		
		         	}else if(inputLine.toUpperCase().equals("CHAT")){
		         		if(joined){
		         		out.println("Who?");
		         		String requestedUser = in.readLine();
		         		
		         		if(requestedUser.equals(user.username)){
		         			out.println("No, do not talk with yourself. Just don't.");
		         		}else if(server.nameExists(requestedUser) && server.getClientThreadByUserName(requestedUser).joined){
		         			out.println("CHAT START");
		         			out.println("Setting up a connection with "+requestedUser);
		         			server.getClientThreadByUserName(requestedUser).sendMessage("CHAT CHAT");
		         			server.getClientThreadByUserName(requestedUser).sendMessage(user.hostname);
		         			server.getClientThreadByUserName(requestedUser).sendMessage(Integer.toString(user.portNumber));
		         			server.getClientThreadByUserName(requestedUser).sendMessage("Chat has been initiated by "+user.username);
		         			
		         			joined = false;
		         			server.getClientThreadByUserName(requestedUser).joined = false;
		         			
		         			server.removeByName(user.username);
		         			server.removeByName(requestedUser);
		         			
		         		}else{
		         			out.println(requestedUser + "is not available to chat");
		         		}
		         		}else{
		         			out.println("That person is not available to chat");
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
		 		//System.out.println("Not going through the loop");

	         }
	        
		}catch(Exception e){//LOoks for exception
        	 
         }
		}
	
	public void sendMessage(String message){
		out.println(message);
	}
	
	}

	
//}

