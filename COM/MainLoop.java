package COM;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class MainLoop {
    public final static int HEIGHT = 1000;
    public final static int WIDTH = 1900;
    public static final String[] SET_VALUES = new String[] {"TesterMonke"};
    public final Set<String> TROOPTYPES = new HashSet<>(Arrays.asList(SET_VALUES));
    private static String selectedName = "TesterMonke";
    protected static int freCum = 5;
    protected static int eneCum = 5;
    protected static Set<Troop> frendlys = new HashSet<>(Arrays.asList
    (new Troop[] {new Troop(new Vektor(100,900),true, "Tower"),new Troop(new Vektor(900, 900), true, "Tower")}));
    protected static Set<Troop> enemys = new HashSet<>(Arrays.asList
    (new Troop[] {new Troop(new Vektor(100,100),false, "Tower"), new Troop(new Vektor(900,100), false, "Tower")}));

    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("COM");
        frame.setSize(new Dimension(WIDTH, HEIGHT));
        frame.setResizable(false);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0,0,0,0);
        c.gridx = 0;
        c.ipadx = 450;
        c.ipady = HEIGHT;
        CumPanel cum = new CumPanel();//aka elixir
        frame.add(cum, c);

        c.gridx = 1;
        c.ipadx = HEIGHT;
        c.ipady = HEIGHT;
        MainPanel playArea = new MainPanel();
        frame.add(playArea, c);

        c.gridx = 2;
        c.ipadx = 450;
        c.ipady = HEIGHT;
        CardPanel cards = new CardPanel();
        frame.add(cards, c);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);



        playArea.addMouseListener(new MouseAdapter() {
    	    @Override
    	    public void mouseClicked(MouseEvent event) {
    		    double x = event.getX() - 25;
    		    double y = event.getY() + 25;
                Vektor location = new Vektor(x, y);
                Troop monke = new Troop(location, true, selectedName);
                if (monke.isOnFrendlyGround(HEIGHT) && freCum > monke.getCost()) {
                    //če je klik playerja biu na frendly area pol ugotoviš kua je objective tega pieca glede na to kam je postaulen in pol ga addas v aktivne monkeyu na gridu
                    monke.pathFind(enemys, HEIGHT);
                    frendlys.add(monke);
                    MainLoop.freCum = MainLoop.freCum-monke.getCost();
                    cum.repaint();
                }
            }
         });

        int i = 0;
        int j = 1;
        int k = 1;
        while (i<2400) {
            if (frendlys.size() == 0) {
                System.out.println("LOSER");
                break;
            }
            if (enemys.size() == 0) {
                System.out.println("WINNER");
                break;
            }



            //zanka de pathfinder frendlyu
            for (Troop freTroop: frendlys) {
                if (freTroop.getName().equals("Bridge")) {
                    continue;
                }
                freTroop.pathFind(enemys, HEIGHT);    
            }
            //zanka za pathfinderja enemyu
             for (Troop eneTroop: enemys) {
                if (eneTroop.getName().equals("Bridge")) {
                    continue;
                }
                eneTroop.pathFind(frendlys, HEIGHT);    
            }
            //zanka za in range pa atack za frendlye in enemye
            for (Troop freTroop: frendlys) {
                //preveris ce je trenutna stevilka iteracije aka time kkr je minil - stevilka iteracije k je tazadnic napadu  cooldown pa ce 
                //je objekt bridge in ce kr kol 
                if ((i-freTroop.getLastAttack() < freTroop.getCool()) || freTroop.getName().equals("Bridge")) {
                    freTroop.move();
                    continue;  
                }
                for (Troop eneTroop: enemys) {
                    if (eneTroop.getName().equals("Bridge")) {
                        continue;
                    }
                        if (freTroop.isInRange(eneTroop)) {
                            freTroop.attack(eneTroop);
                            freTroop.setLastAttack(i);
                        }
                }
                if (freTroop.getLastAttack() != i) {
                    freTroop.move();
                }
                enemys.removeIf(enemy -> enemy.isDead());
            }



            for (Troop eneTroop: enemys) {
                    if ((i-eneTroop.getLastAttack() < eneTroop.getCool()) || eneTroop.getName().equals("Bridge")) {
                        eneTroop.move();
                        continue;  
                    }
                for (Troop freTroop: frendlys) {
                    if (freTroop.getName().equals("Bridge") || eneTroop.getName().equals("Bridge")) {
                        continue;
                    }
                    if (eneTroop.isInRange(freTroop)) {
                        eneTroop.attack(freTroop);
                        eneTroop.setLastAttack(i);
                    }
                    if (freTroop.getLastAttack() != i) {
                    freTroop.move();
                    }
                }
                frendlys.removeIf(frendly -> frendly.isDead());
            }
                i++;
                if (i > 40*j && freCum < 10) {
                    freCum++;
                    j++;
                }
                if (i > 40*k && eneCum < 10) {
                    eneCum++;
                    k++;
                }
                


                playArea.repaint(); // ponoven izris okna
                cum.repaint();
                cards.repaint();
                try {
                    Thread.sleep(50); // počakaj 50 ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        }
    
class CumPanel extends JPanel {
        public CumPanel() {
        super();
        setBackground(Color.MAGENTA);
    }
        @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics = (Graphics2D)g; 
        int width = 450;
        int height = 90; 
        for (int i = 0; i < MainLoop.freCum; i++) {
            graphics.setColor(Color.YELLOW);
            graphics.fillRect(0, i * height, width, height);
            graphics.setColor(Color.BLACK);
            graphics.drawRect(0, i * height, width, height);
        }  
        if (MainLoop.freCum == 10) {
            graphics.setColor(Color.RED);
            graphics.fillRect(0, 10 * height, width, height);
        }
    }
}

class MainPanel extends JPanel {
        public MainPanel() {
        super();
        setBackground(Color.WHITE);
    }
        @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics = (Graphics2D)g; 
        AffineTransform base = graphics.getTransform();
        int picSize = 50;
        for (Troop freTroop: MainLoop.frendlys) {
            graphics.translate(freTroop.getLocation().getX(), freTroop.getLocation().getY());
            graphics.rotate(freTroop.getOrientation());
            graphics.drawImage(freTroop.getPicture(), -picSize/2, -picSize/2, null);
            graphics.setTransform(base);
        }
        for (Troop eneTroop: MainLoop.enemys) {
            graphics.translate(eneTroop.getLocation().getX(), eneTroop.getLocation().getY());
            graphics.rotate(eneTroop.getOrientation());
            graphics.drawImage(eneTroop.getPicture(), -picSize/2, -picSize/2, null);
            graphics.setTransform(base);
        }
    }
}

class CardPanel extends JPanel {
        public CardPanel() {
        super();
        setBackground(Color.MAGENTA);
    }
        @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics = (Graphics2D)g; 
        int width = WIDTH/3-WIDTH/4;
        int height = HEIGHT; 
        for (int i = 0; i < 4; i++) {
            graphics.setColor(Color.YELLOW);
            graphics.fillRect(0, height-i * height, width, height);
            graphics.setColor(Color.BLACK);
            graphics.drawRect(0, height-i * height, width, height);
        }  
    }
}

