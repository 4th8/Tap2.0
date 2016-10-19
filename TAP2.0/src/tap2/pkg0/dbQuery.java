/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tap2.pkg0;

/**
 *
 * @author Usman
 */
public class dbQuery {

    private String All_Location_Field;
    private String insertIntoLocation;

    dbQuery() {
    }

    ;
    
    public void setInsertLocation(String id, String name, String abb) {
        insertIntoLocation = String.format("INSERT INTO location (location_id,full_name,abbreviation)VALUES ('%s', '%s', '%s');", id, name, abb);
    }

    public String getInsertIntoLocation(String id, String name, String abb) {
        insertIntoLocation = String.format("INSERT INTO location (location_id,full_name,abbreviation)VALUES ('%s', '%s', '%s');", id, name, abb);
        return insertIntoLocation;
    }

    public String get_All_Location_Field() {
        All_Location_Field = "SELECT * FROM location;";
        return All_Location_Field;
    }

}
