package com.coderfromscratch.httpserver.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileOperations {
    final String CRLF = "\n\r";
    final byte[] buffer = new byte[2*1024];

    public FileOperations(){}

    public void chunks(OutputStream os , String fileName) throws IOException {
        String response =
                "HTTP/1.1 200 ok" + CRLF + // status line: HTTP_VERSION HTTP_CODE HTTP_MESSAGE
                        "Transfer-Encoded: " + "Chunked" + "\n"
                        + "Content-Type: " + "text/plain" + CRLF + // HEADER
                        CRLF + CRLF;
        os.write(response.getBytes());


        FileInputStream in = new FileInputStream(new File("src/main/resources"+ fileName + ".txt"));
        BufferedInputStream bf = new BufferedInputStream(in);



        while (bf.read(buffer) != -1){
            String str = new String(buffer , StandardCharsets.UTF_8);



            os.write(str.getBytes());
        }

    }
}
