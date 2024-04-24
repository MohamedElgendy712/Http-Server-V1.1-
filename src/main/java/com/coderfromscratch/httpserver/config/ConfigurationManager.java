package com.coderfromscratch.httpserver.config;

import com.coderfromscratch.httpserver.util.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {

    private static ConfigurationManager myConfigurationManager;
    private static Configuration myCurrentConfiguration;

    private ConfigurationManager() {
    }

    public static ConfigurationManager getInstance(){
        if (myConfigurationManager == null){
            myConfigurationManager = new ConfigurationManager();
        }

        return myConfigurationManager;
    }

    /*
    * Used to load a configuration file from a path provided
    * */
    public void loadConfigurationFile(String filePath)  {

        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }

        StringBuffer sb = new StringBuffer();
        int i;

        try {
            while ((i = fileReader.read()) != -1){
                sb.append((char)i);
            }
        } catch (IOException e){
            throw new HttpConfigurationException(e);
        }

        JsonNode config = null;
        try {
            config = Json.parse(sb.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("Error parsing the config file" , e);
        }

        try {
            myCurrentConfiguration = Json.fromJson(config , Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error parsing the config file, Internal" , e);
        }

    }

    /*
    * Returns the current loaded configuration
    * */
    public Configuration getCurrentConfiguration(){

        if (myCurrentConfiguration == null){

            throw new HttpConfigurationException("No current configuration set.");
        }

        return myCurrentConfiguration;

    }



}
