package messenger_project;

public class FriendItem implements Friend {
	 private String nickName;
	    private String fullName;
	    private String lastSeenIP;
	    private String image;
	    
	    public FriendItem(String nickName) {
	        this.nickName = nickName;
	    }

	    public FriendItem(String nickName, String fullName, String lastSeenIP) {
	        this.nickName = nickName;
	        this.fullName = fullName;
	        this.lastSeenIP = lastSeenIP;
	    }
	    
	    public FriendItem(String nickName, String fullName, String lastSeenIP, String image) {
	        this.nickName = nickName;
	        this.fullName = fullName;
	        this.lastSeenIP = lastSeenIP;
	        this.image = image;
	    }
	    

	    @Override
	    public String getNickname(){
	        return nickName;
	    }

	    @Override
	    public String getFullName(){
	        return fullName;
	    }

	    @Override
	    public String getLastSeenIP(){
	        return lastSeenIP;
	    }

	    @Override
	    public String getImage() {
	        return image;
	    }
	    
	    @Override
	    public Chat getChat(){
	        return new ChatLogFile(this).getData();
	    }
	    
	    @Override
	    public boolean addChat(Message message){
	        return new ChatLogFile(this).addChat(message);
	    }

	    @Override
	    public String toString() {
	        return " Nick Name: " + nickName + "\n Full Name: " + fullName + "\n Last Seen IP: " + lastSeenIP + "\n-----------------------------";
	    }

	    


}
