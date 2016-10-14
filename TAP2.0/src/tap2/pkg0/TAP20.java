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
        try {
            //ResultSet rs =dataBase.executeSelect("Select * from location;");
            //rs.next();
            dataBase.executeInsert("INSERT INTO location (location_id,full_name,abbreviation)VALUES ('000', 'DC', 'd');");
            //System.out.println(rs.getFetchSize());
            //db.connectToDB("Select * from location;");
        } catch (SQLException ex) {
            Logger.getLogger(TAP20.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

}
   
