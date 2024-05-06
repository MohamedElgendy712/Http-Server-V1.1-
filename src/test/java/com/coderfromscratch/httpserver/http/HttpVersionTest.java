package com.coderfromscratch.httpserver.http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HttpVersionTest {

    @Test
    void getBestCombatibleVersionExactMatch(){

        HttpVersion version = null;
        try {
            version = HttpVersion.getBestCombatibleVersion("HTTP/1.1");

        } catch (BadHttpVersionException e) {

            fail();
        }

        assertNotNull(version);
        assertEquals(version , HttpVersion.HTTP_1_1);
    }

    @Test
    void getBestCombatibleVersionBadFormat(){

        HttpVersion version = null;
        try {
            version = HttpVersion.getBestCombatibleVersion("http/1.1");
            fail();
        } catch (BadHttpVersionException e) {

        }

    }

    @Test
    void getBestCombatibleVersionHigherVersion(){

        HttpVersion version = null;
        try {
            version = HttpVersion.getBestCombatibleVersion("HTTP/1.2");
            assertNotNull(version);
            assertEquals(version , HttpVersion.HTTP_1_1);
        } catch (BadHttpVersionException e) {
            fail();
        }


    }

}
