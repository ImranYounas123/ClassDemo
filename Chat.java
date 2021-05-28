package messenger_project;
import java.util.*;

public interface Chat {
    public void addMessage(Message msg);

    public List<Message> getMessages();

}
