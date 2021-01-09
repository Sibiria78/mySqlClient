package com.juergen.sqltool;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;

public class DbProperties {
    private static final String URL         = "db.url";
    private static final String USERNAME    = "db.username";
    private static final String PASSWORD    = "db.password";
    private static final String[] REQUIRED_KEYS = {URL, USERNAME, PASSWORD};
    private static final String FILENAME    = "config/db.properties";
    
    private final String        filePath;
    private final Properties    properties  = new Properties();
    
    public DbProperties() {
        final File file = new File(FILENAME);
        filePath = file.getAbsolutePath();
        
        // Ressourcen werden automatisch durch das Java7 feature ARM
        // freigegeben
        try (final InputStream inputStream = new BufferedInputStream(
            new FileInputStream(file))) {
            properties.load(inputStream);
            System.out.println("URL: " + properties.getProperty(URL));
            System.out.println("User: " + properties.getProperty(USERNAME));
            System.out.println("Pwd: " + properties.getProperty(PASSWORD));
            
            //ensureAllKeysAvailable();
            
            // Dieser Aufruf stellt sicher, dass der URL-Wert
            // vorhanden ist.
            final String url = properties.getProperty(URL);
            if (url.isEmpty()) {
                throw new IllegalStateException("DB config file '"+
                        filePath + "' is incomplete! Missing value "+
                        "for key: '" + URL + "'");
            }
        } catch (final IOException io) {
            throw new IllegalStateException("problems while accessing "+
                    "DB config file '" + filePath + "'!", io);
        }
    }
    
    private void ensureAllKeysAvailable() {
        final SortedSet<String> missingKeys = new TreeSet<>();
        
        for (final String key : REQUIRED_KEYS) {
            if (!properties.contains(key)) {
                missingKeys.add(key);
            }
        }
        
        if (!missingKeys.isEmpty()) {
            throw new IllegalStateException("DB config file '"+
                        filePath + "' is incomplete! Missing keys: "+
                        missingKeys);
        }
    }
    
    public String getUrl() {
        return properties.getProperty(URL);
    }
    
    public String getUserName() {
        return properties.getProperty(USERNAME);
    }

    public String getPassword() {
        return properties.getProperty(PASSWORD);
    }
}
