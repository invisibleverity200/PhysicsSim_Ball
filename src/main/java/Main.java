import UserInterface.GUI;
import UserInterface.SimulationCanvas;

import java.io.IOException;

public class Main {
    private static SimulationCanvas canvas;

    public static void main(String[] args) {
        try {
            GUI frame = new GUI();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
