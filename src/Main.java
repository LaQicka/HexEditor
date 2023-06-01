import gui.Application;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {

        FileReader fileReader = new FileReader();
        fileReader.setFilename("Telegram");

        ArrayList<Integer> data = fileReader.getData();

        Application app = new Application(16);
        app.setData(data);
        app.update();
    }

}