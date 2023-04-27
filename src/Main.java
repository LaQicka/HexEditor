import logic.Application;
import logic.ByteFileReader;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {

        ByteFileReader fileReader = new ByteFileReader();
        fileReader.setFilename("inputFile");

        ArrayList<Integer> data = fileReader.getData();

        Application app = new Application(8);
        app.setData(data);
        app.update();

    }

}