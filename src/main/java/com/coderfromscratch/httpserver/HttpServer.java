package com.coderfromscratch.httpserver;

import com.coderfromscratch.httpserver.config.Configuration;
import com.coderfromscratch.httpserver.config.ConfigurationManager;
import com.coderfromscratch.httpserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/*
*
* Driver class for the http server
*
*/
public class HttpServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args){

        LOGGER.info("Server starting...");

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");

        Configuration config = ConfigurationManager.getInstance().getCurrentConfiguration();

        LOGGER.info("Using Port: " + config.getPort());
        LOGGER.info("Using WebRoot " + config.getWebroot());

        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(config.getPort() , config.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
