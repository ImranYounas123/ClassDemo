package messenger_project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendList extends DataManager<Friend> implements DAO<Friend>{

    Map<String,Friend> friends;

    FriendList() {
        super("friends.list");
        friends = new HashMap<String, Friend>();
//        fetchData();
    }

    @Override
    public void fetchData() {
        friends.clear();
        for (String log : super.readLog()) {
            String parts[] = log.split("   ");
            Friend friend = new FriendItem(parts[0].substring( 1, parts[0].length() - 1 ), parts[1].substring(10), parts[2].substring(8));
            friends.put(friend.getNickname(), friend);
        }
    }

    @Override
    public List<Friend> getAll() {
        return new ArrayList<Friend>(friends.values());
    }

    @Override
    public Friend getFiltered(String key) {
        Friend friend = friends.get(key);
        if(friend == null) {
            friend = new FriendItem(key);
        }
        return friend;
    }

    @Override
    public void remove(String key) {
        friends.remove(key);
    }

    @Override
    public void add(Friend data) {
        friends.put(data.getNickname(), data);
        writeLog("<" + data.getNickname() + ">   [FULLNAME]" + data.getFullName() + "   [LASTIP]" + data.getLastSeenIP() + "   [IMAGE]" + data.getImage() + "\n");
    }


}
