import gui.Application;

import java.io.IOException;

// Попробовать сделать многооконное приложение


public class Main {
    public static void main(String[] args) throws IOException {


        Application app = new Application(16);
        app.update();

    }

}