/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juergen.sqltool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Juergen
 */
public class DbAccess {

    public DbAccess() {
        conn = getConnection();
        columns = new ArrayList<>();
    }
    
    private Connection conn;
    
    private List<String> columns;
    
    private String status;
    
    /*
     * How to fix the problem with the time zone:
     * https://stackoverflow.com/questions/50493398/mysql-connector-error-the-server-time-zone-value-central-european-time
    */
    public final Connection getConnection() {
        if (conn != null) {
            return conn;
        }
        try {
            String addMe = "?useLegacyDatetimeCode=false&serverTimezone=Europe/Amsterdam&useSSL=false";
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library"+addMe, "root", "root");
            return conn;
        } catch (Exception e) {
            System.out.println("Error: no DB connection! "+e.getMessage());
        }
        return null;
    }
    
    public ObservableList<Books> executeQuery(Query q) throws Exception {
        if (q.getType().equals(Query.SELECT)) {
            status = null;
            return getBooksList(Query.getDefault());
        }
        int returnValue = q.executeUpdate();
        status = returnValue + " row(s) updated.";
        return getBooksList(Query.getDefault());
    }
    
    public ObservableList<Books> getBooksList(String query) throws Exception {
        ObservableList<Books> bookList = FXCollections.observableArrayList();
        Statement st;
        ResultSet rs;
        
        st = getConnection().createStatement();
        rs = st.executeQuery(query);

        //Retrieving the ResultSetMetadata object
        ResultSetMetaData rsMetaData = rs.getMetaData();
        String tab = rsMetaData.getTableName(1);
        System.out.println("List of column names of table '"+tab+"': ");
        //Retrieving the list of column names
        int count = rsMetaData.getColumnCount();
        for (int i = 1; i <= count; i++) {
            String colName = rsMetaData.getColumnName(i);
            columns.add(colName);
            System.out.println(colName + ", "+rsMetaData.getColumnClassName(i) 
                    + ", "+rsMetaData.getColumnTypeName(i));
        }

        Books books;
        while (rs.next()) {
            Object[] arr = (columns.toArray());
            books = new Books(rs.getInt(String.valueOf(arr[0])), rs.getString(String.valueOf(arr[1])),
                    rs.getString(String.valueOf(arr[2])),
                    rs.getInt(String.valueOf(arr[3])), rs.getInt(String.valueOf(arr[4])));
            bookList.add(books);
        }            
        return bookList;
    }
    
    public List<String> getColumns() {
        return columns;
    }
    
    public String getStatus() {
        return status;
    }

    void removeAllColumns() {
        columns = new ArrayList<>();
    }
}
