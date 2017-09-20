/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.sql.*;

public class DBConnector {

    public DBConnector() {
        createDBConnection();
    }

    public Connection createDBConnection() {
        Connection con = null;

        ResultSet rs;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:80/db", "root", "1234");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;

    }
}
