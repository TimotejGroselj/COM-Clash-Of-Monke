package COM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Start {
    public static int h = MainLoop.HEIGHT;
    public static int w = MainLoop.WIDTH;

    public Start() {;
    }

    public static void main(String[]args){
        JFrame start = new JFrame("Build Your Deck!");
        start.setSize(new Dimension(w, h));
        start.setResizable(false);
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set layout
        start.setLayout(new FlowLayout());

        // Create button
        for (int i = 0;i<4;i++) {
            JButton button = new JButton("+ "+i);
            button.setPreferredSize(new Dimension(100, 40));
            int CardNum = i;
            button.addActionListener(e -> System.out.println("Presoo "+CardNum));
            start.add(button);
        }
        start.setVisible(true);
    }
}

