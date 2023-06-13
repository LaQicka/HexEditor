package FileStaff;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AppFileReader {
    private String filename;
    private ArrayList<Integer> data;

    public AppFileReader(){

    }
    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) throws IOException {
        data = new ArrayList<>();
        this.filename = filename;
        InputStream iStream = new FileInputStream(filename);
        int value;
        while ((value = iStream.read()) !=-1 )data.add(value);
    }
    public ArrayList<Integer> getData() {
        return data;
    }
}
