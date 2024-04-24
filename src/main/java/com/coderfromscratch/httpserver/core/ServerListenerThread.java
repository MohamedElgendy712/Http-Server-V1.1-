package com.coderfromscratch.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);

    private int port;
    private String webRoot;
    private ServerSocket serverSocket;

    public ServerListenerThread(int port, String webRoot) throws IOException {
        this.port = port;
        this.webRoot = webRoot;
        this.serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {

        try {
            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();

                LOGGER.info(" * Connetion Accpeted " + socket.getInetAddress());

                // Make a thread for each connection request to proccessing the requests in parallel
                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
                workerThread.start();
            }
            //serverSocket.close();


        } catch (IOException e) {
            LOGGER.error("Problem with setting socket" , e);
        }finally {
            if (serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {}
            }
        }

    }


}
