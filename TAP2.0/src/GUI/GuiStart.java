/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor
 */
package GUI;

import javax.swing.JFrame;
import tap2.pkg0.db;
import tap2.pkg0.dbQuery;

/**
 *
 * @author marcelo
 */
public class GuiStart {
     public static void main(String[] args) {
        db database = new db();
        dbQuery query = new dbQuery(database);
        dataPage frame = new dataPage(query);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
