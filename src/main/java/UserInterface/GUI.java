package UserInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GUI extends JFrame implements MouseListener {
    JButton start;
    JButton update = new JButton("Update");
    JButton snapshoot = new JButton("Snapshot");
    JButton clear = new JButton("Clear");
    JTextField paricleCount;
    JTextField massField;
    JTextField areaField;
    JSlider dragCoefficentSlider;
    JSlider elasticySlider;
    JSlider frictionCoefficent;
    JTextField gravity;
    JButton reset;
    JLabel height;
    SimulationCanvas canvas;
    int[] ballPos = new int[2];
    JLabel spacer1;
    double[] velVecto = new double[2];
    JTextField airField;
    boolean pause = false;
    CanvasContentContainer canvasPanel;
    ArrayList<double[]> snapshot;


    public GUI() {

        this.setSize(1920, 1080);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel setting = new JPanel();
        GridLayout gridLayout = new GridLayout(2, 12);
        setting.setLayout(gridLayout);

        setting.setSize(1920, 100);

        JLabel particleCount = new JLabel("Particle Count", SwingConstants.CENTER);
        JLabel areaLabel = new JLabel("Area [m^2]", SwingConstants.CENTER);
        JLabel dragCoefficent = new JLabel("Drag coefficient : 0.5", SwingConstants.CENTER);
        JLabel elasticy = new JLabel("Elastic coefficient : 0.5", SwingConstants.CENTER);
        JLabel gravityLabel = new JLabel("Gravity [m/s^2]", SwingConstants.CENTER);
        JLabel massLabel = new JLabel("Mass [kg]", SwingConstants.CENTER);
        JLabel frictionLabel = new JLabel("Friction coefficient: 0.5", SwingConstants.CENTER);
        JLabel densityLabel = new JLabel("Air Density [kg/m^3]", SwingConstants.CENTER);
        height = new JLabel("Height : " + ballPos[1] + " m", SwingConstants.CENTER);

        start = new JButton("Start");

        TextFieldKeyAdapter textFieldKeyAdapter = new TextFieldKeyAdapter();

        paricleCount = new JTextField("1");
        paricleCount.setHorizontalAlignment(JTextField.CENTER);
        paricleCount.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                JTextField field = (JTextField) ke.getSource();
                System.out.println(ke.getKeyChar());
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                    field.setEditable(true);
                } else {
                    field.setEditable(false);
                    field.setBackground(Color.white);
                    field.setText("");
                }
            }
        });


        areaField = new JTextField("0.001");
        areaField.setHorizontalAlignment(JTextField.CENTER);
        areaField.addKeyListener(textFieldKeyAdapter);

        airField = new JTextField("1.25");
        airField.setHorizontalAlignment(JTextField.CENTER);
        airField.addKeyListener(textFieldKeyAdapter);

        dragCoefficentSlider = new JSlider(0, 100, 50);
        dragCoefficentSlider.addChangeListener((ChangeEvent e) -> {
            JSlider source = (JSlider) e.getSource();

            double dAngle = (double) source.getValue() / 100;
            dragCoefficent.setText("Drag coefficent: " + dAngle);
        });

        elasticySlider = new JSlider(0, 100, 50);
        elasticySlider.addChangeListener((ChangeEvent e) -> {
            JSlider source = (JSlider) e.getSource();

            double dAngle = (double) source.getValue() / 100;
            elasticy.setText("Elasticy coefficent: " + dAngle);
        });

        frictionCoefficent = new JSlider(0, 1000, 500);
        frictionCoefficent.addChangeListener((ChangeEvent e) -> {
            JSlider source = (JSlider) e.getSource();

            double dAngle = (double) source.getValue() / 1000;
            frictionLabel.setText("Friction coefficient: " + dAngle);
        });


        gravity = new JTextField("9.81");
        gravity.setHorizontalAlignment(JTextField.CENTER);
        gravity.addKeyListener(new TextFieldKeyAdapter());

        reset = new JButton("Pause");

        massField = new JTextField("1");
        massField.addKeyListener(new TextFieldKeyAdapter());
        massField.setHorizontalAlignment(JTextField.CENTER);

        JButton spacer = new JButton();
        spacer.setOpaque(false);
        spacer.setContentAreaFilled(false);
        spacer.setBorderPainted(false);

        spacer1 = new JLabel("Velocity: 0 m/s");


        JButton spacer2 = new JButton();
        spacer2.setOpaque(false);
        spacer2.setContentAreaFilled(false);
        spacer2.setBorderPainted(false);

        setting.add(spacer1);
        setting.add(particleCount);
        setting.add(massLabel);
        setting.add(areaLabel);
        setting.add(densityLabel);
        setting.add(dragCoefficent);
        setting.add(elasticy);
        setting.add(frictionLabel);
        setting.add(gravityLabel);
        setting.add(height);
        setting.add(snapshoot);
        setting.add(clear);

        setting.add(start);
        setting.add(paricleCount);
        setting.add(massField);
        setting.add(areaField);
        setting.add(airField);
        setting.add(dragCoefficentSlider);
        setting.add(elasticySlider);
        setting.add(frictionCoefficent);
        setting.add(gravity);
        setting.add(update);
        setting.add(reset);

        canvasPanel = new CanvasContentContainer();

        this.add(setting);
        this.add(canvasPanel);

        canvas = new SimulationCanvas(canvasPanel);

        ButtonListener listener = new ButtonListener(this, canvas);

        reset.addActionListener(listener);
        start.addActionListener(listener);
        snapshoot.addActionListener(listener);
        clear.addActionListener(listener);
        update.addActionListener(listener);

        canvas.addMouseListener(this);

        this.setSize(1920, 10);
        this.setSize(1920, 1080); //FIXME without this buttons do not appear


    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Graphics g = canvas.getGraphics();
        drawEnv(g);
        ballPos[0] = e.getX() - 10;
        ballPos[1] = e.getY() - 10;
        height.setText("Height : " + (910 - ballPos[1]) / 100.00 + " m");

        try {
            g.drawImage(ImageIO.read(new File("C:\\Users\\airph\\Pictures\\Tennis-Ball-icon.png")), ballPos[0], (int) ballPos[1], 20, 20, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Graphics g = canvas.getGraphics();
        drawEnv(g);
        g.drawLine(ballPos[0] + 10, ballPos[1] + 10, e.getX(), e.getY());
        g.drawOval(e.getX() - 5, e.getY() - 5, 10, 10);
        float VelocityVectorLength = (float) Math.sqrt(Math.pow((ballPos[0] + 10 - e.getX()) / 50, 2) + Math.pow((ballPos[1] + 10 - e.getY()) / 50.00, 2));
        try {
            g.drawImage(ImageIO.read(new File("C:\\Users\\airph\\Pictures\\Tennis-Ball-icon.png")), ballPos[0], (int) ballPos[1], 20, 20, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        spacer1.setText("Velocity: " + VelocityVectorLength + " [m/s]");
        velVecto[0] = -(ballPos[0] + 10 - e.getX()) / 50.00;
        velVecto[1] = -(ballPos[1] + 10 - e.getY()) / 50.00;

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void drawEnv(Graphics g) {
        g.clearRect(0, 0, 1920, 1080);
        try {
            g.drawImage(ImageIO.read(new File("C:\\Users\\airph\\Pictures\\Wiese.png")), 0, 910, 1920, 1090 - 910, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
