package messenger_project;
import java.util.*;   

public interface Chat {
    public void addMessage(Message msg);
    public void subMessage(Message msg);
    public List<Message> getMessages();
    

}
