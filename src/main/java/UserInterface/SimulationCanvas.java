package UserInterface;

import Physics.Enteties.Particle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SimulationCanvas extends Canvas {
    public SimulationCanvas(CanvasContentContainer frame) {
        this.setSize(1920, 1080);
        this.setLocation(0, 0);
        frame.add(this);
        this.createBufferStrategy(4);
    }

    void draw(ArrayList<Particle> p, ArrayList<double[]> snapshot) throws IOException {
        Graphics g = this.getBufferStrategy().getDrawGraphics();
        g.clearRect(0, 0, 1920, 1080);
        g.drawImage(ImageIO.read(new File("C:\\Users\\airph\\Pictures\\Wiese.png")), 0, 910, 1920, 1090 - 910, null);

        for (Particle particle : p) {
            for (int i = 0; i < particle.getPrevPositions().size(); i++) {
                g.drawOval((int) particle.getPrevPositions().get(i)[0], (int) particle.getPrevPositions().get(i)[1], 3, 3);

            }
            if (snapshot != null) {
                g.setColor(Color.green);
                for (int i = 0; i < snapshot.size() && i < particle.getPrevPositions().size(); i++) {
                    g.drawOval((int) snapshot.get(i)[0], (int) snapshot.get(i)[1], 3, 3);
                }
                g.setColor(Color.black);
            }
            g.drawImage(ImageIO.read(new File("C:\\Users\\airph\\Pictures\\Tennis-Ball-icon.png")), (int) particle.getPosition()[0] - 10, (int) particle.getPosition()[1] - 10, 20, 20, null);
           if(snapshot != null) if (snapshot.size() > particle.getPrevPositions().size())
                g.drawImage(ImageIO.read(new File("C:\\Users\\airph\\Pictures\\Tennis-Ball-icon.png")), (int) snapshot.get(particle.getPrevPositions().size() - 1)[0] - 10, (int) snapshot.get(particle.getPrevPositions().size() - 1)[1] - 10, 20, 20, null);
        }
        g.dispose();

        this.getBufferStrategy().show();
    }

}
