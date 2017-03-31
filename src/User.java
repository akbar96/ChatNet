
/*This class is used to assign or call values/parameters such as the ones listed below*/

public class User {

	
	String username;
	String ipAddress;
	int portNumber;
	String hostname;
	
	public User(){			//Initializing the parameters
		username = "";
		ipAddress = "";
		portNumber = 0;
	}
	
	public User(String username, String ipAddress, int portNumber){		//Assigning values to the parameters
		this.username = username;
		this.ipAddress = ipAddress;
		this.portNumber = portNumber;
	}
	
}
