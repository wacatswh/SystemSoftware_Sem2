/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import javax.swing.SwingWorker;
import java.util.*;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.awt.event.ActionEvent;

/**
 *
 * @author seoww
 */

public class SocketReader extends SwingWorker<Void, String> {

    private static List<ActionListener> actionListeners;

    public SocketReader() {
        actionListeners = new ArrayList<>(25);
    }

    public static void addActionListener(ActionListener listener) {
        actionListeners.add(listener);
    }

    public static void removeActionListener(ActionListener listener) {
        actionListeners.remove(listener);
    }

    @Override
    protected Void doInBackground() throws Exception {
        System.out.println("Connected to Server!");

        try (DataInputStream in = new DataInputStream(SocketManager.INSTACNE.getInputStream())) {

            System.out.println("Before setting text area");

            String serverInput = null;
            do {
                // HANDLE INPUT PART HERE
                serverInput = in.readUTF();

                if (serverInput != null) {
                    System.out.println("Read " + serverInput);
                    publish(serverInput);
                }

            } while (!serverInput.equals("/close"));
            System.out.println("Program closed");
        }
        return null;
    }

    @Override
    protected void process(List<String> chunks) {
        for (String text : chunks) {
            ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, text);
            for (ActionListener listener : actionListeners) {
                listener.actionPerformed(evt);
            }
        }
    }

}