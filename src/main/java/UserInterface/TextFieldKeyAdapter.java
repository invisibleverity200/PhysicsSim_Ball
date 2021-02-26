package UserInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TextFieldKeyAdapter extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent ke) {
        JTextField field = (JTextField)  ke.getSource();
        System.out.println(ke.getKeyChar());
        if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || ke.getKeyChar() == '.') {
            field.setEditable(true);
        } else {
            field.setEditable(false);
            field.setBackground(Color.white);
            field.setText("");
        }
    }
}
