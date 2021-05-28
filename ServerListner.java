package messenger_project;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
public class ServerListner extends Thread {
	  
    String ip;
    int port;
    GUI messenger;
    PrintWriter dos;
    BufferedReader dis;
    FriendItem me = new FriendItem("EURAKART","EURAKARTE","","N/A");

    public ServerListner(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.start();
    }

    @Override
    public void run() {
        try
        {
            
                    
            Socket s = new Socket(ip, port);
            
            dos = new PrintWriter(s.getOutputStream());
            dis = new BufferedReader(new InputStreamReader(s.getInputStream()));
            
            System.out.println("listening to server...");
            
            writeMessage("<REGISTER><" + me.getNickname() + "><" + me.getFullName() + "><" + InetAddress.getLocalHost().getHostAddress().toString() + "><" + me.getImage()+ ">");
            
            messenger = new GUI(this, me);
            
            while (!s.isClosed())  
            {
                
                String received = dis.readLine();
                System.out.println("Received: " + received);
                
                if(received == null || received.trim().equals("")){
                    continue;
                }
                
                if(received.startsWith("<FRIEND>")) {
                    String parts[] = received.split("><");
                    messenger.addFriend(parts[1], parts[2], parts[3], parts[4].substring(0, parts[4].length() - 1));
                }
                else if(received.startsWith("<LOGOUT>")) {
                    String parts[] = received.split("><");
                    messenger.removeFriend(parts[1].substring(0, parts[1].length() - 1));
                }
                else if(received.startsWith("<PRIVATE>")) {
                    String parts[] = received.split("><");
                    messenger.addChatMessage(parts[0].substring(1), parts[1], parts[2].substring(0, parts[2].length() - 1));
                }
                else if(received.startsWith("<PUBLIC>")) {
                    
                    String parts[] = received.split("><");
                    messenger.addChatMessage(parts[0].substring(1), parts[1], parts[2].substring(0, parts[2].length() - 1));
                    
                }
                if(received.equals("Exit")){
                    break;
                }
            } 
            System.out.println("listening ended...");
            
            
        }catch(Exception e){
            e.printStackTrace(); 
        } 
    }
    
    public boolean writeMessage(String message) {
        System.out.println("Sending: " + message);
        dos.println(message);
        dos.flush();
        return true;
    }
    
    public void end(){
        try {
            writeMessage("<LOGOUT><" + me.getNickname() + ">");
            dis.close();
            dos.close(); 
        } catch (IOException ex) {
            Logger.getLogger(ServerListner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
