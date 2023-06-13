import FileStaff.AppFileReader;
import gui.Application;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {

        AppFileReader appFileReader = new AppFileReader();
        appFileReader.setFilename("Telegram");

//        ArrayList<Integer> data = appFileReader.getData();

        Application app = new Application(16);
//        app.setData(data);
        app.update();
    }

}