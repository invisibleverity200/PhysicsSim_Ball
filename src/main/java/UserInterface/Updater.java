package UserInterface;

import Physics.Enteties.Particle;
import Physics.Enteties.PhysicEngine;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Updater implements Runnable {
    private SimulationCanvas canvas;
    private ArrayList<Particle> p;
    private Graphics g;
    KillSwitch kill;
    private GUI gui;

    public Updater(SimulationCanvas canvas, ArrayList<Particle> p, KillSwitch kill, GUI gui) {
        this.canvas = canvas;
        this.p = p;
        this.kill = kill;
        this.gui = gui;
    }

    @Override
    public void run() {
        boolean[] groundState = new boolean[]{false, false};
        while (!kill.kill) {
            if (!gui.pause) {
                try {
                    Thread.sleep(10);
                    int idx = 0;
                    for (Particle particle : p) {
                        double[] collisionVector = PhysicEngine.getResVectorCollision(p, idx);
                        particle.setGroundState(particle.calculateNewPosition(
                                PhysicEngine.calculateForceVector(particle, particle.getGroundState(),
                                        PhysicEngine.calculateDragVector(particle.getArea(), particle.getVelocity(), particle.getAirDensity(), particle.getDragC()),
                                        collisionVector
                                )
                                , 0.01));

                        gui.spacer1.setText("Velocity: " + ((float) PhysicEngine.getVectorValue(particle.getVelocity())) + " [m/s]");
                        gui.height.setText("Height : " + (float) (910 - particle.getPosition()[1]) / 100 + " m");
                        idx++;
                    }

                    canvas.draw(p, gui.snapshot);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(""); //FIXME without that the continue function is not working
            }
        }
        System.out.println("Thread CLosed!");
    }


}
