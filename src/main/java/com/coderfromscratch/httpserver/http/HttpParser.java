package com.coderfromscratch.httpserver.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

    private static final int SP = 0x20; //32
    private static final int CR = 0x0D; //13
    private static final int LF = 0x0A; //10

    public HttpRequest parseHttpRequest(InputStream inputStream) throws HttpParsingException{

        InputStreamReader reader = new InputStreamReader(inputStream);

        HttpRequest request = new HttpRequest();

        try {
            parseRequestLine(reader , request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        parseHeaders(reader , request);
        parseBody(reader , request);

        return request;

    }

    private void parseRequestLine(InputStreamReader reader, HttpRequest request) throws IOException, HttpParsingException {
        StringBuilder processingDataBuffer = new StringBuilder();

        boolean methodParsed = false;
        boolean requestTargetParsed = false;

        int _byte;
        while ((_byte = reader.read()) >= 0){
            if (_byte == CR){
                _byte = reader.read();
                if (_byte == LF){

                    LOGGER.debug("Request Line Vesrion To Process: {}" , processingDataBuffer.toString());

                    // if the method or the request target are not parsed this mean that is an empty request
                    if (!methodParsed || !requestTargetParsed){
                        throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                    }

                    return;
                }
            }

            if (_byte == SP){

                if (!methodParsed){
                    LOGGER.debug("Request Line METHOD To Process: {}" , processingDataBuffer.toString());
                    request.setMethod(processingDataBuffer.toString());
                    methodParsed = true;
                }else if (!requestTargetParsed){
                    LOGGER.debug("Request Line Req Target To Process: {}" , processingDataBuffer.toString());
                    requestTargetParsed = true;
                }else {
                    // if the method and requestTarget are parsed and we find another (SP)  this mean invalid number of items or invalid request line (BAD REQUEST)
                    throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                }
                processingDataBuffer.delete(0 , processingDataBuffer.length());

            }else {

                processingDataBuffer.append((char)_byte);

                // check the length of the method name
                if (!methodParsed && processingDataBuffer.length() > HttpMethod.MAX_LENGTH){

                    throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);

                }

            }

        }
    }

    private void parseHeaders(InputStreamReader reader, HttpRequest request) {

    }

    private void parseBody(InputStreamReader reader, HttpRequest request) {

    }
}
