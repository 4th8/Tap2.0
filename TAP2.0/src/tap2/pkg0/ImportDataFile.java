/*
 
 */
package tap2.pkg0;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Reads in the .csv file and adds the data to the database.
 * Will automatically find the S/N of the Sensor and use that to query the
 * database to find the location of that sensor. If no location exists will query
 * the user to input the location and update the database with that location / SN combo
 * @author Nathan
 * 
 */
public class ImportDataFile {
    private final String filename; //Filename for the input file
    private final dbQuery db;
    
    /**
     * Default Constructor
     * @param filename Filename of the .xlsx file.
     * @param db dbQueary object. An interface with the database.
     */
    ImportDataFile(String filename, dbQuery db){
        this.filename = filename;
        this.db = db;
        execute();
    }
    /**
     * Does most of the work for Importing the file into the database.
     * @author Nathan
     */
    private void execute(){
        try {
            
            XSSFWorkbook workbook = new XSSFWorkbook(filename);
            XSSFSheet sheet = workbook.getSheetAt(0);
             
             
        } catch (IOException ex) {
            Logger.getLogger(ImportDataFile.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        
}
    
}
