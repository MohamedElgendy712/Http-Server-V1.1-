package com.coderfromscratch.httpserver.http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum HttpVersion {

    HTTP_1_1("HTTP/1.1" , 1 , 1);

    public final String LITERAL;
    public final int MAJOR;
    public final int MINOR;

    HttpVersion(String LITERAL, int MAJOR, int MINOR) {
        this.LITERAL = LITERAL;
        this.MAJOR = MAJOR;
        this.MINOR = MINOR;
    }

    private static final Pattern httpVersionRegexPattern = Pattern.compile("HTTP/(?<MAJOR>\\d+).(?<MINOR>\\d+)");

    public static HttpVersion getBestCombatibleVersion(String literalVersion) throws BadHttpVersionException {

        Matcher matcher = httpVersionRegexPattern.matcher(literalVersion);

        if (!matcher.find() || matcher.groupCount() != 2){

            throw new BadHttpVersionException();
        }

        int major = Integer.parseInt(matcher.group("MAJOR"));
        int minor = Integer.parseInt(matcher.group("MINOR"));

        HttpVersion tempBestCompatible = null;

        for (HttpVersion version : HttpVersion.values()){
            if (version.LITERAL.equals(literalVersion)){
                return version;
            }

            if (version.MAJOR == major){
                if (version.MINOR < minor){
                    tempBestCompatible = version;
                }
            }
        }

        return tempBestCompatible;
    }
}