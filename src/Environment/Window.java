package Environment;

import Environment.Environment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Window extends JFrame implements KeyListener {
    static int defaultWidth = 800;
    static int getDefaultHeight = 800;
    static Color defaultBackGroundColor = new Color(0,255,255);
    static int borderWidth = 100;
    static int borderHeight = 50;

    Environment env;

    public Window(int w, int h) {
        super();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setBackground(defaultBackGroundColor);
        JPanel contentPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.env = new Environment(w-borderWidth, h-borderHeight);
        contentPane.add(this.env);
        this.setContentPane(contentPane);
        this.setPreferredSize(new Dimension(w,h));

        this.addKeyListener(this);

        this.pack();
        this.setVisible(true);
    }
    public Environment getEnv() {
        return this.env;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_1) {
            this.env.setType("Water");
        } if (e.getKeyCode() == KeyEvent.VK_2) {
            this.env.setType("Inert");
        } if (e.getKeyCode() == KeyEvent.VK_3) {
            this.env.setType("Carbon");
        } if (e.getKeyCode() == KeyEvent.VK_4) {
            this.env.setType("Heavy");
        } if (e.getKeyCode() == KeyEvent.VK_5) {
            this.env.setType("Light");
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (this.env.getStatus() == "Running") this.env.setStatus("Paused");
            else this.env.setStatus("Running");
        } else if (e.getKeyCode() == KeyEvent.VK_C) {
            this.env.setStatus("Clear");
        } else if (e.getKeyCode() == KeyEvent.VK_B) {
            this.env.toggleDrawBoard();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
