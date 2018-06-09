/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appbuilder.api.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author psilva
 */
public class MyConnectionFactory {

    private static String user = "root";
    private static String password = "root";
    private static String database = "";
    private static String host = "localhost";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://" + host + "/" + database, user, password);
    }

}
