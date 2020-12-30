/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juergen.sqltool;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juergen
 */
public class Query {

    public static final String SELECT = "SELECT";
    public static final String INSERT = "INSERT";
    public static final String UPDATE = "UPDATE";
    public static final String DELETE = "DELETE";
    public static final String defaultQuery = "SELECT * from books";
    
    private final String query;
    private final Connection conn;
    private String type;
    private boolean isValid = false;

    public Query(String query, Connection conn) {
        this.query = query;
        this.conn = conn;
        checkValidity();
        try {
            addToHistory();
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getQuery() {
        return query;
    }

    public static String getDefault() {
        return defaultQuery;
    }

    public String getType() {
        return type;
    }
    
    private void checkValidity() {
        this.type = determineType();
        // TODO: improve the code
        this.isValid = (query != null && query.length() > 10 && this.type != null);
    }
    
    private String determineType() {
        String upper = this.query.toUpperCase();
        if (upper.contains(SELECT)) {
            return SELECT;
        } else if (upper.contains(UPDATE)) {
            return UPDATE;
        } else if (upper.contains(DELETE)) {
            return DELETE;
        } else if (upper.contains(INSERT)) {
            return INSERT;
        }
        return null;
    }
    
    public boolean isValid() {
        return isValid;
    }
    
    public int executeUpdate() throws Exception {
        Statement st = conn.createStatement();
        return st.executeUpdate(getQuery());
    }

    private void addToHistory() throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate("INSERT INTO sqlhist (command) values ('"+getQuery()+"')");
    }
}
