package messenger_project;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
public class GUI implements ActionListener{
	JFrame frame;
    JMenuBar menuBar;
    JMenu fileMenu, showMenu;
    JMenuItem exitMenuItem;
    JCheckBoxMenuItem privateChatMenuItem, publicChatMenuItem;
    JLabel chatLabel, friendLabel;
    JScrollPane chatTextAreaScroll, friendsListScroll;
    JTextArea chatTextArea;
    JList<String> friendsList;
    JTextField messageTextField;
    JButton sendButton;

    FriendList friends;
    DefaultListModel<String> friendListItems;

    FriendItem me;
    ServerListner server;
    boolean privateMode;

    GUI(ServerListner server, FriendItem me) {
        this.me = me;
        this.server = server;
        friends = new FriendList();
        friends.clearLog();

        frame = new JFrame();
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        showMenu = new JMenu("Show");
        exitMenuItem = new JMenuItem("Exit");
        chatLabel = new JLabel("Chat");
        friendLabel = new JLabel("Friends List");
        privateChatMenuItem = new JCheckBoxMenuItem("Private chat");
        publicChatMenuItem = new JCheckBoxMenuItem("Public chat");
        messageTextField = new JTextField("");
        sendButton = new JButton("Send");
        chatTextArea = new JTextArea();
        chatTextArea.setEditable(false);
        chatTextAreaScroll = new JScrollPane(chatTextArea);
        friendListItems = new DefaultListModel<>();
        friendsList = new JList<>(friendListItems);
        friendsListScroll = new JScrollPane(friendsList);
        exitMenuItem.addActionListener(this);
        privateChatMenuItem.addActionListener(this);
        publicChatMenuItem.addActionListener(this);

        friendsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (!lse.getValueIsAdjusting()) {
                    String chat = null;
                    try {
                        chat = friendsList.getSelectedValue().toString();
                    } catch (Exception e) {

                    }
                    if (chat != null) {
                        chatLabel.setText("Chat: " + chat);
                        updateSelectedChat(chat);
                    }
                }
            }
        });
        sendButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                String message = messageTextField.getText();
                if (!message.equals("")) {
                    if (!privateMode) {
                        server.writeMessage("<PUBLIC><" + me.getNickname() + "><" + message + ">");
                        me.addChat(new Message(me, message));
                        updateSelectedChat(me.getNickname());
                    } else {
                        String messageTo = friendsList.getSelectedValue().toString();
                        server.writeMessage("<PRIVATE><" + me.getNickname() + "><" + messageTo + "><" + message + ">");
                        Friend friend = friends.getFiltered(messageTo);
                        friend.addChat(new Message(me, message));
                        updateSelectedChat(friend.getNickname());
                    }
                    messageTextField.setText("");
                }
            }
        });
        fileMenu.add(exitMenuItem);
        showMenu.add(privateChatMenuItem);
        showMenu.add(publicChatMenuItem);
        menuBar.add(fileMenu);
        menuBar.add(showMenu);
        chatLabel.setBounds(10, 10, 360, 20);
        friendLabel.setBounds(380, 10, 360, 20);
        chatTextAreaScroll.setBounds(10, 30, 360, 290);
        friendsListScroll.setBounds(380, 30, 200, 320);
        messageTextField.setBounds(10, 320, 280, 30);
        sendButton.setBounds(290, 320, 80, 30);
        frame.add(menuBar);
        frame.add(chatLabel);
        frame.add(friendLabel);
        frame.add(chatTextAreaScroll);
        frame.add(friendsListScroll);
        frame.add(messageTextField);
        frame.add(sendButton);
        frame.setJMenuBar(menuBar);
        frame.setLayout(null);
        frame.setSize(600, 415);
        frame.setVisible(true);
        frame.setResizable(false);

        getFriendsList();

        privateChatMenuItem.setSelected(true);
        privateMode = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == exitMenuItem) {
            server.end();
            System.exit(0);
        }
        if (e.getSource() == privateChatMenuItem) {
            if (friendListItems.size() > 0) {
                friendsList.setSelectedIndex(0);
                updateSelectedChat(friendsList.getSelectedValue().toString());
                chatLabel.setText("Chat: " + friendsList.getSelectedValue().toString());
            }
            else {
                chatLabel.setText("Chat: ");
            }
            friendsList.setEnabled(true);
            publicChatMenuItem.setSelected(false);
            privateChatMenuItem.setSelected(true);
            privateMode = true;
        }
        if (e.getSource() == publicChatMenuItem) {
            chatLabel.setText("Chat: Public");
            friendsList.setSelectedIndex(-1);
            friendsList.setEnabled(false);
            updateSelectedChat(me.getNickname());
            publicChatMenuItem.setSelected(true);
            privateChatMenuItem.setSelected(false);
            privateMode = false;
        }
    }

    private void getFriendsList() {
        friendListItems.clear();
        for (Friend friend : friends.getAll()) {
            if (!friend.getNickname().equals(me.getNickname())) {
                friendListItems.addElement(friend.getNickname());
            }
        }
        friendsList.setModel(friendListItems);
        if (friendListItems.size() > 0) {
            friendsList.setSelectedIndex(0);
            updateSelectedChat(friendsList.getSelectedValue().toString());
        }

    }

    public void addFriend(String nickName, String fullName, String lastSeenIP, String image) {
        Friend friend = new FriendItem(nickName, fullName, lastSeenIP, image);
        friends.add(friend);
        getFriendsList();
    }

    public void removeFriend(String nickName) {
        friends.remove(nickName);
        getFriendsList();
    }

    private void updateSelectedChat(String name) {
        chatTextArea.setText("");
        Friend friend = friends.getFiltered(name);
        if (friend != null) {
            Chat chat = friend.getChat();
            if (chat.getMessages().size() > 0) {
                for (Message message : chat.getMessages()) {
                    chatTextArea.append(message.toString() + "\n");
                    chatTextArea.setCaretPosition(chatTextArea.getDocument().getLength());
                }
            } else {
                chatTextArea.append("No cht History found");
            }

        }

    }

    public void addChatMessage(String mode, String author, String message) {
        Friend friend = friends.getFiltered(author);
        if (friend != null) {
            if (mode.equals("PUBLIC")) {
                me.addChat(new Message(friend, message));
                if (!privateMode) {
                    updateSelectedChat(me.getNickname());
                }
            } else if (mode.equals("PRIVATE")) {
                friend.addChat(new Message(friend, message));
                if (privateMode && friendsList.getSelectedValue().toString().equals(friend.getNickname())) {
                    updateSelectedChat(friend.getNickname());
                }
            }
        }

    }


}
