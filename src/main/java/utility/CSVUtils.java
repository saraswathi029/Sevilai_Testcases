package utility;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class CSVUtils {
    /**
     * Reads a CSV file from the specified resource path.
     * 
     * @param resourcePath The relative path to the CSV file in the resources folder.
     * @return A list of string arrays, where each array represents a row in the CSV file.
     * @throws IOException
     * @throws CsvException
     */
    public static List<String[]> readCSV(String resourcePath) throws IOException, CsvException {
        // Construct the absolute path to the CSV file
        String absolutePath = System.getProperty("user.dir") + "/src/test/resources/" + resourcePath;

        // Read the CSV file
        try (CSVReader csvReader = new CSVReader(new FileReader(absolutePath))) {
            List<String[]> data = new ArrayList<>(csvReader.readAll());
            return data;
        }
    }
}
