/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.net.Socket;
import java.io.*;
import java.util.Objects;

/**
 *
 * @author seoww
 */
public enum SocketManager {
    INSTACNE;

    private String host = "localhost";
    private int port = 1337;
    private Socket socket;

    public Socket open() throws IOException {
        if (socket != null) {
            close();
        }
        socket = new Socket(host, port);
        return socket;
    }

    public void close() throws IOException {
        if (socket == null) {
            return;
        }
        socket.close();
    }

    public boolean isOpen() {
        return socket != null
            && socket.isConnected()
            && !socket.isClosed()
            && !socket.isInputShutdown()
            && !socket.isOutputShutdown();
    }

    public InputStream getInputStream() throws IOException {
        Objects.requireNonNull(socket, "Socket is not open");
        return socket.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        Objects.requireNonNull(socket, "Socket is not open");
        return socket.getOutputStream();
    }
}