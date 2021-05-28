package messenger_project;

//import java.util.ArrayList;
import java.util.*;

public class ChatItem implements Chat {
    List<Message> messages;

    public ChatItem() {
        messages = new ArrayList<>();
    }
    
    @Override
    public void addMessage(Message msg){
        messages.add(msg);
    }
    
    @Override
    public List<Message> getMessages(){
        return messages;
    }

}
