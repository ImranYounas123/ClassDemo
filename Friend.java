package messenger_project;

public interface Friend {
	 public String getNickname();
	    public String getFullName();
	    public String getLastSeenIP();
	    public String getImage();
	    public Chat getChat();
	    public boolean addChat(Message message);

}
