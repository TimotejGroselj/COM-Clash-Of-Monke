package COM;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

public class MainLoop {
    public final static int HEIGHT = 1000;
    public final static int WIDTH = 1900;

    public static final String[] SET_VALUES = new String[] {"TesterMonke", "basic monke", "Wizard", "Super"};
    public static final Set<String> TROOPTYPES = new HashSet<>(Arrays.asList(SET_VALUES));
    private static String selectedName = "TesterMonke";
    protected static int indSelectedName = 0;

    protected static int freCum = 5; //elixir globaln za risanje
    protected static int eneCum = 5;
    protected static int i = 0; //globalni timer

    protected static Map<Troop, Troop> animations = new HashMap<Troop, Troop>(); //tuki grejo troopi k nucajo animacijo
    protected static List<String> troopSelection= new ArrayList<String>();
    
    protected static Set<Troop> frendlys = new HashSet<>(Arrays.asList
    (new Troop[] {new Troop(new Vektor(100,900),true, "Tower"),new Troop(new Vektor(900, 900), true, "Tower")}));
    protected static Set<Troop> enemys = new HashSet<>(Arrays.asList
    (new Troop[] {new Troop(new Vektor(100,100),false, "Tower"), new Troop(new Vektor(900,100), false, "Tower")}));
    //začetni troopi aka sam towerji
    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 4; i++) {
            for (String troop: TROOPTYPES) {    
            if (!troopSelection.contains(troop)) {
                troopSelection.add(troop);
                //break; ker mava premal troopov
            }
        }
        }

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
        //frame creation in organizacija

        playArea.addMouseListener(new MouseAdapter() {
    	    @Override
    	    public void mouseClicked(MouseEvent event) {
    		    double x = event.getX() - 25;
    		    double y = event.getY() + 25;
                Vektor location = new Vektor(x, y);
                Troop monke = new Troop(location, true, selectedName);
                if (monke.isOnFrendlyGround(HEIGHT) && freCum >= monke.getCost()) {
                    //če je klik playerja biu na frendly area pol ugotoviš kua je objective tega pieca glede na to kam je postaulen in pol ga addas v aktivne monkeyu na gridu
                    monke.pathFind(enemys, HEIGHT);
                    frendlys.add(monke);
                    MainLoop.freCum = MainLoop.freCum-monke.getCost();
                    cum.repaint();
                }
            }
         });
         cards.setLayout(new GridLayout(4,1));

         JButton but1 = new JButton();
        but1.setVisible(false);
         but1.setBounds(0,0,450,250);
         but1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                MainLoop.selectedName = MainLoop.troopSelection.get(0);
            }
         });
         cards.add(but1);

         JButton but2 = new JButton();
         but2.setVisible(false);
         but2.setBounds(0,250,450,250);
         but2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainLoop.selectedName = MainLoop.troopSelection.get(1);
            }
         });
         cards.add(but2);

        JButton but3 = new JButton();
        but3.setVisible(false);
         but3.setBounds(0,500,450,250);
         but3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainLoop.selectedName = MainLoop.troopSelection.get(2);
            }
         });
         cards.add(but3);

        JButton but4 = new JButton();
        but4.setVisible(false);
         but4.setBounds(0,750,450,250);
         but4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainLoop.selectedName = MainLoop.troopSelection.get(3);
            }
         });
        cards.add(but4);
        boolean test = true;
        while (i<2400) {
            //če poteče cajt konča igro
            //pregleda če je kdo zgubu aka nima več nobenga monketa
            if (frendlys.size() == 0) {
                System.out.println("LOSER");
                break;
            }
            if (enemys.size() == 0) {
                System.out.println("WINNER");
                break;
            }




            //zanka za pathfinder frendlyu
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
                //preveris ce je trenutna stevilka iteracije aka time kkr je minil - stevilka iteracije k je tazadnic napadu < cooldown pa ce 
                //je objekt bridge in ce kr kol od tega skipas troopa
                if (!((i-freTroop.getLastAttack() < freTroop.getCool()) || freTroop.getName().equals("Bridge"))) {
                    //prevers use enemye in ce je kir u range pa ni bridge ga napades 
                    for (Troop eneTroop: enemys) {
                        if (eneTroop.getName().equals("Bridge")) {
                            continue;
                        }
                        if (freTroop.isInRange(eneTroop)) {
                            freTroop.attack(eneTroop);
                            animations.put(freTroop, eneTroop);
                            freTroop.setLastAttack(i);
                            break;
                        }
                    }
                }
                //prever a je kkrsn kol enemy u rangu in se premakne ce ni nobenga
                for (Troop eneTroop: enemys) {
                    if (freTroop.isInRange(eneTroop)) {
                        test = false;
                        break;
                    }
                    else {
                        test = true;
                    }
                }
                if (test) {
                    freTroop.move();
                }
                //odstrani vse mrtve monkeye
                enemys.removeIf(enemy -> (enemy.isDead()));
            }

            //isto sranje k uzgori sam obratno
            for (Troop eneTroop: enemys) {
                if (!((i-eneTroop.getLastAttack() < eneTroop.getCool()) || eneTroop.getName().equals("Bridge"))) {
                    for (Troop freTroop: frendlys) {
                        if (freTroop.getName().equals("Bridge")) {
                            continue;
                        }
                        if (eneTroop.isInRange(freTroop)) {
                            eneTroop.attack(freTroop);
                            animations.put(eneTroop, freTroop);
                            eneTroop.setLastAttack(i);
                            break;
                        }
                    }
                }   
                for (Troop freTroop: frendlys) {
                    if (eneTroop.isInRange(freTroop)) {
                        test = false;
                        break;
                    }
                    else {
                        test = true;
                    }
                }
                if (test) {
                    eneTroop.move();
                }
                frendlys.removeIf(frendly -> (frendly.isDead()));
            }
                //spremeni globaln timer
            i++;
            //pogleda če lhka prišteje elixer in če lahko ga
            if (i % 40 == 0 && freCum < 10) {
                freCum++;
            }
            if (i % 40 == 0 && eneCum < 10) {
                eneCum++;

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
            graphics.drawImage(freTroop.getPicture(), -picSize/2, -picSize/2, picSize, picSize, null);
            graphics.setTransform(base);
        }
        for (Troop eneTroop: MainLoop.enemys) {
            graphics.translate(eneTroop.getLocation().getX(), eneTroop.getLocation().getY());
            graphics.rotate(eneTroop.getOrientation());
            graphics.drawImage(eneTroop.getPicture(), -picSize/2, -picSize/2, picSize, picSize, null);
            graphics.setTransform(base);
        }
        
        MainLoop.animations.keySet().removeIf(animatroop -> (MainLoop.i-animatroop.getLastAttack() > 6));
        for (Troop attackingTroop: MainLoop.animations.keySet()) {
            graphics.translate(attackingTroop.getLocation().getX(), attackingTroop.getLocation().getY());
            graphics.rotate(attackingTroop.getOrientation());
            graphics.drawImage(attackingTroop.getAnimation(),(int) Vektor.dist(MainLoop.animations.get(attackingTroop).getLocation(), attackingTroop.getLocation())/2-picSize/2, -picSize/8, picSize/2, picSize/4, null);
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
        graphics.setFont(new Font("Montserrat", Font.BOLD, 25));
        for (int i = 0; i < 4; i++) {
            Troop troop = new Troop(new Vektor(0,0), true, MainLoop.troopSelection.get(i));
            graphics.drawImage(troop.getPicture(),0,i*250, 250, 250, null);
            graphics.drawString("cost: " + troop.getCost() + "CUM", 250, i*250+40);
            graphics.drawString("health: " + troop.getMaxhealth() + "HP", 250, i*250+80);
            graphics.drawString("range: " + troop.getRange()/30, 250, i*250+120);
            graphics.drawString("damage: " + troop.getDamage()+ "HP", 250, i*250+160);
            graphics.drawString("cool: " + troop.getCool()*0.05 + "s", 250, i*250+200);
            graphics.drawString("speed: " + troop.getSpeed(), 250, i*250+240);
            graphics.drawRect(0, i*250, 450, 250);
        }  
    }
}


