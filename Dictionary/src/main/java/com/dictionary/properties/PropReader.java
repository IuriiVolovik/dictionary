package com.dictionary.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropReader {
    static private Properties properties = new Properties();

    static {
        InputStream input = null;

        try {
            input = new FileInputStream("test.properties");
            properties.load( input );
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if ( input != null ) {
                try {
                    input.close();
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        }
    }

    static public String get(String key) {
        return (String) properties.get(key);
    }
}
