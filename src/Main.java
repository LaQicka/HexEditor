import gui.Application;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {

        FileReader fileReader = new FileReader();
        fileReader.setFilename("Telegram");

        ArrayList<Integer> data = new ArrayList<>();

        Application app = new Application(4);
        app.setData(data);
        app.update();
    }

}