/*
 
 */
package tap2.pkg0;


import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Reads in the .csv file and adds the data to the database. Will automatically
 * find the S/N of the Sensor and use that to query the database to find the
 * location of that sensor. If no location exists will query the user to input
 * the location and update the database with that location / SN combo
 *
 * @author Nathan
 *
 */
public class ImportDataFile {

    private final String filename; //Filename for the input file
    private final dbQuery db;

    /**
     * Default Constructor
     *
     * @param filename Filename of the .csv file.
     * @param db dbQueary object. An interface with the database.
     */
    ImportDataFile(String filename, dbQuery db) {
        this.filename = filename;
        this.db = db;
    }

    /**
     * Does most of the work for Importing the file into the database.
     *
     * @author Nathan
     */

}
