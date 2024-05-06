package com.coderfromscratch.httpserver.core;

import com.coderfromscratch.httpserver.http.HttpParser;
import com.coderfromscratch.httpserver.http.HttpParsingException;
import com.coderfromscratch.httpserver.http.HttpRequest;
import com.coderfromscratch.httpserver.util.FileOperations;
import com.sun.deploy.net.HttpResponse;
import com.sun.deploy.net.MessageHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.www.protocol.http.HttpURLConnection;

import java.io.*;
import java.net.Socket;
import java.net.URL;

public class HttpConnectionWorkerThread extends Thread {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);

    private Socket socket;
    private FileOperations fileOperations;

    public HttpConnectionWorkerThread(Socket socket){

        this.socket = socket;
        fileOperations = new FileOperations();
    }


    @Override
    public void run() {

        InputStream inputStream = null;
        OutputStream outputStream = null;
        HttpParser httpParser = null;

       try {

           inputStream = socket.getInputStream();
           outputStream = socket.getOutputStream();
           httpParser = new HttpParser();
           HttpRequest request = null;

           try {
               request = httpParser.parseHttpRequest(inputStream);
           } catch (HttpParsingException e) {
               e.printStackTrace();
           }

           // Stream the requested data in chunks to the client
           fileOperations.chunks(outputStream , request.getRquestTarget());
                      
           /*
           String html = "<html><head><title>Simlpe java HTTP Server</title></head><body><h1>This page was served by using simple java HTTP server</h1></body></html>";

           final String CRLF = "\n\r"; //13 , 10

           String response =
                   "HTTP/1.1 200 ok" + CRLF + // status line: HTTP_VERSION HTTP_CODE HTTP_MESSAGE
                           "Content-Length: " + html.getBytes().length + "\n"
                           + "Transfer-Encoded: " + "Chunked" + "\n"
                           + "Content-Type: " + "text/plain" + CRLF + // HEADER
                           CRLF +
                           html +
                           CRLF + CRLF;

           outputStream.write(response.getBytes());
           */


           LOGGER.info("Connection processing Finshed.");

       }catch (IOException e) {
           LOGGER.error("Problem with communication" , e);
       }finally {

           if (inputStream != null){
               try {
                   inputStream.close();
               } catch (IOException e) {}
           }

           if (outputStream != null){
               try {
                   outputStream.close();
               } catch (IOException e) {}
           }

           if (socket != null){
               try {
                   socket.close();
               } catch (IOException e) {}
           }

       }

    }
}
