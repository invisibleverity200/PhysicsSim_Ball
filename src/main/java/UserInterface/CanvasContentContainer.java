package UserInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CanvasContentContainer extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            g.drawImage(ImageIO.read(new File("C:\\Users\\airph\\Pictures\\Background.png")), 0, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
