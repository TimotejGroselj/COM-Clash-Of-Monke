package COM;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class Start extends JFrame{
    public static int h = MainLoop.HEIGHT;
    public static int w = MainLoop.WIDTH;
    private static JButton first = null;
    protected static List<String> deck = new ArrayList<>();
    protected static List<String> rest = new ArrayList<>();
    protected static boolean running = true;
    protected static List<String> pictures = new ArrayList<>();
    protected static List<String> actual_deck = new ArrayList<>();

    public Start() throws IOException {
        JFrame start = new JFrame("Build Your Deck!");
        start.setSize(new Dimension(w, h));
        start.setResizable(false);
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        int width = 100;
        int height = 100;
        int space = 50;
        int xt = 4 * width + 3 * space;
        int rxt = (w - xt) / 2;
        int yt = 4 * height + 3 * space;
        int ryt = (h - yt) / 2;

        File foldr = new File("pictures");
        File[] allpics = foldr.listFiles();


        assert allpics != null;
        for (File file:allpics){
            if (!file.getName().equals("tower.png"))
            {pictures.add(file.getName());}
        }

        for (int i = 1; i < 9; i++) {
            deck.add(Integer.toString(i));
        }
        for (int i = 9; i < 17; i++) {
            rest.add(Integer.toString(i));
        }


        ActionListener SwapCards = e -> {
            JButton chosen = (JButton) e.getSource();
            if (first == null) {
                first = chosen;
                first.setBorder(new LineBorder(Color.RED,5));
            } else {
                String temp = first.getText();
                String casu = chosen.getText();
                Icon temp1 = first.getIcon();
                if (first != chosen) {
                    if (deck.contains(temp) && rest.contains(casu)) {
                        deck.remove(temp);
                        deck.add(casu);
                        rest.remove(casu);
                        rest.add(temp);
                    } else {
                        deck.remove(casu);
                        deck.add(temp);
                        rest.remove(temp);
                        rest.add(casu);
                    }
                    first.setIcon(chosen.getIcon());
                    first.setText(chosen.getText());
                    chosen.setIcon(temp1);
                    chosen.setText(temp);
                    first.setBorder(UIManager.getBorder("Button.border"));
                }
                first = null;
            }
        };

        JButton ready = new JButton();
        ready.setText("READY");
        ready.setBackground(Color.GREEN);
        ready.setBounds(1400, 160, 100, 100);
        ready.addActionListener(e -> {
            if (deck.size() == 8) {start.dispose();running = false;}
        });
        start.add(ready);


        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < 4; i++) {
                JButton button = new JButton();
                int CardNum = (i + 1) + j * 4;
                button.setIcon(new ImageIcon("pictures/"+pictures.get(CardNum)));
                button.setText(Integer.toString(CardNum));
                int x = rxt + i * (width + space);
                int y = ryt - 150 + j * (height + space);
                button.setBounds(x, y, width, height);
                button.addActionListener(SwapCards);
                start.add(button);
            }
        }
        int yt2 = 9 * height + 8 * space;
        int ryt2 = (h - yt2) / 2;
        int add = 0;
        for (int j = 0; j < 2; j++) {
            for (int i = 9; i < 13; i++) {
                int CardNum = i + j*4;
                JButton button = new JButton();
                button.setIcon(new ImageIcon("pictures/"+pictures.get(CardNum)));
                button.setText(Integer.toString(CardNum));
                int x = i * (width + space);
                int y = ryt2 + (height + space);
                button.setBounds(x - 675, y + 600 + add, width, height);
                button.addActionListener(SwapCards);
                start.add(button);
            }
            add += 150;
        }

        start.setLayout(null);


        ImageIcon cur = new ImageIcon("layout_pictures/Wood.png");
        Image wood = cur.getImage().getScaledInstance(1000, 500, Image.SCALE_DEFAULT);
        ImageIcon fin = new ImageIcon(wood);
        JLabel fin1 = new JLabel(fin);
        fin1.setBounds(450, -110, 1000, 500);
        start.add(fin1);


        ImageIcon seamonk = new ImageIcon("layout_pictures/SeaMonke.png");
        Image seemon = seamonk.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT);
        ImageIcon seemonfin = new ImageIcon(seemon);
        JLabel fin2 = new JLabel(seemonfin);
        fin2.setBounds(20, 350, 250, 250);
        start.add(fin2);


        ImageIcon tren = new ImageIcon("layout_pictures/Lake.png");
        Image sea = tren.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT);
        ImageIcon see = new ImageIcon(sea);
        JLabel funsee = new JLabel(see);
        funsee.setBounds(0, 0, w, h);
        start.add(funsee);
        start.setVisible(true);


        while (running){
            //System.out.println(actual_deck);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> getActual_deck(){
        for (String num:deck){
            int i = Integer.parseInt(num);
            actual_deck.add(MainLoop.TROOPTYPES[i]);
        }
        return actual_deck;
    }

}

