/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tap2.pkg0;

import java.sql.SQLException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marcelo
 */
public class TAP20 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String see;
        db dataBase = new db();
        dbQuery query = new dbQuery();
        try {
            ResultSet rs =dataBase.executeSelect(query.getLocationIdBySerialNumber("k"));
            rs.next();
            //dataBase.executeInsert(query.insertTemperatureData("1998-01-08 04:05:06", 5,"k","000" ));
            //dataBase.executeInsert(query.insertSensor("k", "D"));
            System.out.println(rs.getString(1));
            //db.connectToDB("Select * from location;");
        } catch (SQLException ex) {
            Logger.getLogger(TAP20.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
