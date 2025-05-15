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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


public class MainLoop {
    public final static int HEIGHT = 1000;
    public final static int WIDTH = 1900;
    public static final String[] SET_VALUES = new String[] {"TesterMonke"};
    public final Set<String> TROOPTYPES = new HashSet<>(Arrays.asList(SET_VALUES));
    private static String selectedName = "TeseterMonke";
    protected static int freCum = 5;
    protected static int eneCum = 5;
    protected static Set<Troop> frendlys = new HashSet<>(Arrays.asList
    (new Troop[] {new Troop(new Vektor(100,-980),true, "Tower"),new Troop(new Vektor(980, -980),true, "Tower")}));
    protected static Set<Troop> enemys = new HashSet<>(Arrays.asList
    (new Troop[] {new Troop(new Vektor(100,-100),false, "Tower"),new Troop(new Vektor(980,-100),false, "Tower")}));

    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("COM");
        frame.setSize(new Dimension(WIDTH, HEIGHT));
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        MainPanel playArea = new MainPanel();
        frame.add(playArea, BorderLayout.CENTER);

        CumPanel cum = new CumPanel();//aka elixir
        cum.setMinimumSize(new Dimension(420,HEIGHT));
        frame.add(cum, BorderLayout.WEST);
        CardPanel cards = new CardPanel();
        cards.setMinimumSize(new Dimension(420,HEIGHT));
        frame.add(cards, BorderLayout.EAST);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);



        //nrdi set frendly pa enemy k vsebuje towerje pa bridge
        Grid grid = new Grid(frendlys, enemys, HEIGHT, HEIGHT);
        int i = 0;
        playArea.addMouseListener(new MouseAdapter() {
    	    @Override
    	    public void mouseClicked(MouseEvent event) {
    		    double x = (double) event.getX();
    		    double y = (double) -event.getY();
                Vektor location = new Vektor(x, y);
                Troop monke = new Troop(location, true, selectedName);
                if (grid.isOnFrendlyGround(monke)) {
                    //če je klik playerja biu na frendly area pol ugotoviš kua je objective tega pieca glede na to kam je postaulen in pol ga addas v aktivne monkeyu na gridu
                    monke.pathFind(grid);
                    frendlys.add(monke);
                    MainLoop.freCum = MainLoop.freCum-monke.getCost();
                    cum.repaint();
                }
            }
         });




        while (i<1) {
            //zanka de pathfinder frendlyu
            for (Troop freTroop: frendlys) {
                if (freTroop.getName().equals("Bridge")) {
                    continue;
                }
                freTroop.pathFind(grid);    
            }
            //zanka za pathfinderja enemyu
             for (Troop eneTroop: enemys) {
                if (eneTroop.getName().equals("Bridge")) {
                    continue;
                }
                eneTroop.pathFind(grid);    
            }
            //zanka za in range pa atack za frendlye in enemye
            for (Troop freTroop: frendlys) {
                for (Troop eneTroop: enemys) {
                    if (freTroop.getName().equals("Bridge") || eneTroop.getName().equals("Bridge")) {
                        continue;
                    }
                    if (freTroop.isInRange(eneTroop)) {
                        if (i-freTroop.getLastAttack() <= freTroop.getCool()) {
                            freTroop.attack(eneTroop);
                            freTroop.setLastAttack(i);
                        }
                    }
                    else {
                        freTroop.move();
                    }
                }
            }
            for (Troop eneTroop: enemys) {
                for (Troop freTroop: frendlys) {
                    if (freTroop.getName().equals("Bridge") || eneTroop.getName().equals("Bridge")) {
                        continue;
                    }
                    if (i-eneTroop.getLastAttack() <= eneTroop.getCool()) {
                            eneTroop.attack(freTroop);
                            eneTroop.setLastAttack(i);
                    }
                    else {
                        eneTroop.move();
                    }
                    }
                }
                i++;
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
        int width = 420;
        int height = 100; 
        for (int i = 0; i < MainLoop.freCum; i++) {
            graphics.setColor(Color.YELLOW);
            graphics.fillRect(0, i * height, width, height);
            graphics.setColor(Color.BLACK);
            graphics.drawRect(0, i * height, width, height);
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
        for (Troop freTroop: MainLoop.frendlys) {
            graphics.translate(freTroop.getLocation().getX(), -HEIGHT+freTroop.getLocation().getY());
            graphics.rotate(freTroop.getOrientation());
            graphics.drawImage(freTroop.getPicture(), 100, 100, null);
            graphics.setTransform(base);
        }
        for (Troop eneTroop: MainLoop.enemys) {
            graphics.translate(eneTroop.getLocation().getX(), -HEIGHT+eneTroop.getLocation().getY());
            graphics.rotate(eneTroop.getOrientation());
            graphics.drawImage(eneTroop.getPicture(), 100, 100, null);
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

