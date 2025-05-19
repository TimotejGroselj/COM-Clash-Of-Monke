package COM;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class Start {
    public static int h = MainLoop.HEIGHT;
    public static int w = MainLoop.WIDTH;
    private static JButton first = null;


    public Start() {;
    }

    public static void main(String[]args){
        JFrame start = new JFrame("Build Your Deck!");
        start.setSize(new Dimension(w, h));
        start.setResizable(false);
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int width = 100;
        int height = 100;
        int space = 50;
        int xt= 4*width + 3*space;
        int rxt = (w-xt)/2;
        int yt = 4*height + 3*space;
        int ryt = (h-yt)/2;

        Set<String> deck = new HashSet<>();
        for (int i = 1;i < 9;i++){deck.add(Integer.toString(i));}
        Set<String> rest = new HashSet<>();
        for (int i = 9;i < 25;i++){rest.add(Integer.toString(i));}



        ActionListener SwapCards = e -> {
            JButton chosen = (JButton) e.getSource();
            if (first == null) {
                first = chosen;
            } else {
                String temp = first.getText();
                String casu = chosen.getText();
                if (first != chosen){
                    if (deck.contains(temp) && rest.contains(casu)){
                        deck.remove(temp);
                        deck.add(casu);
                        rest.remove(casu);
                        rest.add(temp);
                    }
                    else {
                        deck.remove(casu);
                        deck.add(temp);
                        rest.remove(temp);
                        rest.add(casu);
                    }
                System.out.println(deck);
                System.out.println(rest);
                first.setText(chosen.getText());
                chosen.setText(temp);
                }
                first = null;
            }
        };


        for (int j = 0;j < 2;j++) {
            for (int i = 0; i < 4; i++) {
                JButton button = new JButton();
                int CardNum = (i+1) + j*4;
                button.setText(Integer.toString(CardNum));
                int x = rxt + i * (width+space);
                int y = ryt-150 + j * (height+space);
                button.setBounds(x, y, width, height);
                button.addActionListener(SwapCards);
                start.add(button);
            }
        }
        int yt2 = 9*height + 8*space;
        int ryt2 = (h-yt2)/2;
        int add =0;
        for (int j = 0;j <4;j++) {
            for (int i = 1; i < 5; i++) {
                int CardNum = i + (8+j*4);
                JButton button = new JButton();
                button.setText(Integer.toString(CardNum));
                int x = i * (width + space);
                int y = ryt2 + (height + space);
                button.setBounds(x - 100, y + 400+ add, width, height);
                button.addActionListener(SwapCards);
                start.add(button);
            }
            add += 150;
        }


        start.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        JPanel north = new JPanel();
        north.add(new JLabel("YOUR DECK"));
        start.add(north,BorderLayout.NORTH);
        panel.setBackground(Color.YELLOW);
        panel.setBounds(0,0,w,h);
        start.add(panel);
        start.setVisible(true);
    }
}

