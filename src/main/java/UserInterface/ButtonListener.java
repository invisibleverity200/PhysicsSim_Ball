package UserInterface;

import Physics.Enteties.Particle;
import Physics.Enteties.ParticleType;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class ButtonListener implements ActionListener {
    private volatile ArrayList<Particle> list = new ArrayList<>();
    private GUI gui;
    private KillSwitch kill = new KillSwitch();
    private SimulationCanvas canvas; //FIXME
    private boolean stop = false;

    ButtonListener(GUI gui, SimulationCanvas canvas) {
        this.gui = gui;
        this.canvas = canvas;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gui.start) {
            eventStart();
        } else if (e.getSource() == gui.reset) {
            eventPause();
        } else if (e.getSource() == gui.clear) {
            eventClearSnapShoot();
        } else if (e.getSource() == gui.snapshoot) {
            eventTakeSnapShoot();
        } else if (e.getSource() == gui.update) {
            eventUpdate();
        }

    }

    private void eventStart() {
        if (!stop) {
            stop = true;
            gui.start.setText("Stop");
            kill.kill = false;
            list.clear();
            int particleCount = Integer.valueOf(gui.paricleCount.getText());
            double mass = Double.valueOf(gui.massField.getText());
            double area = Double.valueOf(gui.areaField.getText());
            double gravity = Double.valueOf(gui.gravity.getText());
            double drag = gui.dragCoefficentSlider.getValue() / 100.00;
            double elasity = gui.elasticySlider.getValue() / 100.00;
            double[] position = new double[]{gui.ballPos[0], gui.ballPos[1]};
            double cFriction = gui.frictionCoefficent.getValue() / 1000.00;
            double airDensity = Double.valueOf(gui.airField.getText());

            for (int i = 0; i < particleCount; i++) {
                list.add(new ParticleType(mass, position, Arrays.copyOf(gui.velVecto, gui.velVecto.length), gravity, area, drag, elasity, cFriction, airDensity));
            }
            new Thread(new Updater(canvas, list, kill, gui)).start();
        } else {
            gui.start.setText("Start");
            kill.kill = true;
            stop = false;
        }
    }

    private void eventPause() {
        if (!gui.pause) {
            gui.pause = true;
            gui.reset.setText("Continue");
        } else {
            gui.pause = false;
            gui.reset.setText("Pause");
        }
    }

    private void eventTakeSnapShoot() {
        gui.snapshot = new ArrayList<>(list.get(0).getPrevPositions());
    }

    private void eventClearSnapShoot() {
        gui.snapshot = null;
    }

    private void eventUpdate() {
        int particleCount = Integer.valueOf(gui.paricleCount.getText());
        double mass = Double.valueOf(gui.massField.getText());
        double area = Double.valueOf(gui.areaField.getText());
        double gravity = Double.valueOf(gui.gravity.getText());
        double drag = gui.dragCoefficentSlider.getValue() / 100.00;
        double elasity = gui.elasticySlider.getValue() / 100.00;
        double[] position = new double[]{gui.ballPos[0], gui.ballPos[1]};
        double cFriction = gui.frictionCoefficent.getValue() / 1000.00;
        double airDensity = Double.valueOf(gui.airField.getText());
//new ParticleType(mass, position, Arrays.copyOf(gui.velVecto,gui.velVecto.length), gravity, area, drag, elasity, cFriction, airDensity)
        for (int i = 0; i < particleCount; i++) {
            Particle p = list.get(i);
            p.setMass(mass);
            p.setGravity(gravity);
            p.setArea(area);
            p.setAirDensity(airDensity);
            p.setDrag(drag);
            p.setCFriction(cFriction);
            p.setElasity(elasity);
        }
    }
}
