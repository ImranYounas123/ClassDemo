package messenger_project;
import java.util.*;
public class Message {
	 private Friend author;
	    private String message;

	    public Message(Friend author, String message) {
	        this.author = author;
	        this.message = message;
	    }

	    public Friend getAuthor(){
	        return author;
	    }

	    public String getMessage(){
	        return message;
	    }

	    @Override
	    public String toString() {
	        return author.getNickname() + ": " + message;
	    }
	    

}
