package messenger_project;

public class ChatLogFile extends DataManager<Message> {
	private Friend friend;
    private Chat messages;

    ChatLogFile(Friend friend) {
        super(friend.getNickname() + ".log");
        this.friend = friend;
        messages = new ChatItem();
        fetchData();
    }
    
    public Chat getData() {
        return messages;
    }
    
    public boolean addChat(Message message) {
        return writeLog("<" + message.getAuthor().getNickname() + ">" + message.getMessage()+"\n");
    }

    @Override
    public void fetchData() {
        FriendList friends = new FriendList();
        for (String log : super.readLog()) {
            String parts[] = log.split(">", 2);
            if(parts.length>1){
                Friend author = friends.getFiltered(parts[0].substring(1));
                if(author != null){
                    Message message = new Message(author, parts[1]);
                    messages.addMessage(message);
                }
            }
            
        }
    }


}
