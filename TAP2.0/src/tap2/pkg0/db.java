package tap2.pkg0;

import java.sql.*;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Usman
 */
public class db {

    private String query;
    private static Connection connection = null;

    db() {

    }

    public static void connectToDB() {
        System.out.println("-------- PostgreSQL "
                + "JDBC Connection Testing ------------");

        try {

            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {

            System.out.println("Where is your PostgreSQL JDBC Driver? "
                    + "Include in your library path!");
            e.printStackTrace();
            return;

        }

        System.out.println("PostgreSQL JDBC Driver Registered!");

        try {

            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/tap", "postgres",
                    "suk");

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;

        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");

        } else {
            System.out.println("Failed to make connection!");
        }
    }

    public ResultSet executeSelect(String query) throws SQLException {
      //String query = "select * from location;";
        //db test;
        //test=new db();
        this.connectToDB();
        Statement st = null;
        ResultSet rs = null;
        st = connection.createStatement();
        rs = st.executeQuery(query);
      //rs.next();
        //System.out.print(rs.getString(3));
        return rs;
    }

    public void executeInsert(String query) throws SQLException {
      //String query = "select * from location;";
        //db test;
        //test=new db();
        this.connectToDB();
        Statement st = null;
        //ResultSet rs=null;
        st = connection.createStatement();
        st.executeUpdate(query);
    }
}
