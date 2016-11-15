/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tap2.pkg0;

import GUI.dataPage;
import java.sql.SQLException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Marcelo
 */
public class TAP20 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        db database = new db();
        dbQuery query = new dbQuery(database);
        dataPage frame = new dataPage(query);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
