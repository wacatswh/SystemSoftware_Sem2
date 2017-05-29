/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 *
 * @author wacats
 */
public class ChatServer {

    static ArrayList<Socket> socketArrayList = new ArrayList<>();
    
    public static void main(String args[]) {
        final int port = 1337;
        
        ServerSocket serverSocket = null;
        Socket socket = null;
        
        try {
            serverSocket = new ServerSocket(port);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
        while(true) {
            try {
                socket = serverSocket.accept();
                socketArrayList.add(socket);
                new Thread(new Handler(socket)).start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
            
    }
    
    static class Handler implements Runnable {
        private Socket socket;

        public Handler(Socket s) {
            socket = s;
        }

        @Override
        public void run() {
            try {
                String inMessage = "";

                while (true) {
                    System.out.println("Waiting");
                    System.out.println("Connected");
                    DataInputStream in = new DataInputStream(socket.getInputStream());
//                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                    do {
                        inMessage = in.readUTF();

                        if(inMessage != null) {
                            for(Socket ss : socketArrayList) {
                                DataOutputStream out = new DataOutputStream(ss.getOutputStream());
                                out.writeUTF(inMessage);
                            }
                        }
                    } while(!inMessage.equals("/close"));
                    socket.close();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        }
    }
}
