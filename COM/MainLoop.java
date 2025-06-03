package COM;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

public class MainLoop {
    public final static int HEIGHT = 1000;
    public final static int WIDTH = 1900;
    public final static int TIMELIMIT = 3600;
    public static final String[] TROOPTYPES = new String[] {"Acid thrower", "Alien","Monke","Wizard", "Bionic bommer", "Bomerang",
            "Bommerang master","Canon","Catapult","CHIPPER","Engineer", "Fire wizard",
            "Flame thrower","Freze tower","Jaka","Laser gunner", "Minigun","Mortar",
            "Ninja", "Plasma super", "Sniper", "Super", "Timo","Ice wizard"}; //tega ne slati por favor
    protected static String selectedName;
    protected static List<String> troopSelection= new ArrayList<String>();
    protected static BufferedImage BANANA;
    protected static BufferedImage backround;
    protected static BufferedImage bananaBack;
    protected static int freElix = 5; //elixir globaln za risanje
    protected static int eneElix = 5;
    protected static int i = 0; //globalni timer
    protected static String situation;

    protected static Map<Troop, Troop> animations = new HashMap<Troop, Troop>(); //tuki grejo troopi k nucajo animacijo

    
    protected static Set<Troop> frendlys = new HashSet<>(Arrays.asList
    (new Troop[] {new Troop(new Vektor(200,800),true, "Tower"),new Troop(new Vektor(800, 800), true, "Tower")}));
    protected static Set<Troop> enemys = new HashSet<>(Arrays.asList
    (new Troop[] {new Troop(new Vektor(200,200),false, "Tower"), new Troop(new Vektor(800,200), false, "Tower")}));
    //začetni troopi aka sam towerji
    public static void main(String[] args) throws IOException {
        try {
            BANANA = ImageIO.read(new File("layout_pictures","BANANA.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            backround = ImageIO.read(new File("layout_pictures","backround.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bananaBack = ImageIO.read(new File("layout_pictures","bananaBack.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Start uga = new Start();
        List<String> deck = Start.deck;

        for (int i = 0; i < 4; i++) {
            for (String troop: deck) {
            if (!troopSelection.contains(troop)) {
                troopSelection.add(troop);
                break;
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
        CumPanel elix = new CumPanel();
        frame.add(elix, c);

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
                //če je klik playerja biu na frendly area pol ugotoviš kua je objective pieca kkr ga hocs placat (kr je shranjeno v selected name)
                //glede na to kam je postaulen (nastavs angle proti najbižjemu enemyu)
                //in pol ga addas v aktivne monkeyu na gridu
                if (!(MainLoop.selectedName == null)) {
                    double x = event.getX() - 25;
                    double y = event.getY() + 25;
                    Vektor location = new Vektor(x, y);
                    Troop monke = new Troop(location, true, MainLoop.selectedName);
                    if (monke.isOnFrendlyGround(HEIGHT) && freElix >= monke.getCost()) {
                        monke.pathFind(enemys);
                        frendlys.add(monke);
                        MainLoop.freElix = MainLoop.freElix-monke.getCost();
                        int ind = MainLoop.troopSelection.indexOf(MainLoop.selectedName);
                        Random random = new Random();
                        while (true) {
                            String name = deck.get(random.nextInt(deck.size()));
                            //String name = MainLoop.TROOPTYPES[random.nextInt(MainLoop.TROOPTYPES.length)];
                            if (!MainLoop.troopSelection.contains(name)) {
                                MainLoop.troopSelection.set(ind, name);
                                MainLoop.selectedName = null;
                                break;
                            }
                        }   
                        cards.repaint();
                        elix.repaint();
                    }
                }
            }
         });
         //do vrstice 160 je sam ustvarjanje 4 gumbov k določjo indeks kir piece izberemo iz izbire
         cards.setLayout(new GridLayout(4,1));

         JButton but1 = new JButton();
         but1.setBounds(0,0,450,250);
         but1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainLoop.selectedName = MainLoop.troopSelection.get(0);
            }
         });
         cards.add(but1);

         JButton but2 = new JButton();
         but2.setBounds(0,250,450,250);
         but2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainLoop.selectedName = MainLoop.troopSelection.get(1);
            }
         });
         cards.add(but2);

        JButton but3 = new JButton();
         but3.setBounds(0,500,450,250);
         but3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainLoop.selectedName = MainLoop.troopSelection.get(2);
            }
         });
         cards.add(but3);

        JButton but4 = new JButton();
         but4.setBounds(0,750,450,250);
         but4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainLoop.selectedName = MainLoop.troopSelection.get(3);
            }
         });
        cards.add(but4);
        Map<Troop, HashMap<Troop,Boolean>> MonkeFights = new Interactions(TROOPTYPES).getInteractions();

        Troop closestTroop;
        while (i<TIMELIMIT) {
            //če poteče cajt konča igro
            //pregleda če je kdo zgubu aka nima več nobenga monketa

            Troop MonkeNemesis = new MonkeThinker(frendlys,eneElix,MonkeFights).getDude();
            if (MonkeNemesis != null){enemys.add(MonkeNemesis);eneElix-=MonkeNemesis.getCost();}
            

            //zanka za actione frendlyu
            for (Troop freTroop: frendlys) {
                //pathfind določ angle do najbližjiga in vrne ta tropp k je najbižji
                closestTroop = freTroop.pathFind(enemys);
                if (closestTroop.equals(null)) {
                    i = TIMELIMIT;
                    break;
                }
                //če je ta najbližji troop v range se nocmo premaknt in pol gledamo naprej 
                if (freTroop.isInRange(closestTroop)) {
                    //če je troop ready za attack aka je minil več časa od prejšnga attacka kkr je troopou cooldown napade najbižjega
                    if (i-freTroop.getLastAttack() >= freTroop.getCool()) {
                        freTroop.attack(closestTroop);
                        animations.put(freTroop, new Troop(freTroop, freTroop.getAnimation(), closestTroop.getLocation()));
                        freTroop.setLastAttack(i);
                        //če ga ubijemo ga removamo
                        if (closestTroop.isDead()) {
                            enemys.remove(closestTroop);
                        }
                    }
                } else {
                    freTroop.move();
                }
            }
            //zanka za actione enemy k je ista k zgori
             for (Troop eneTroop: enemys) {
                closestTroop = eneTroop.pathFind(frendlys);    
                if (closestTroop.equals(null)) {
                    i = TIMELIMIT;
                    break;
                }
                if (eneTroop.isInRange(closestTroop)) {
                    if (i-eneTroop.getLastAttack() >= eneTroop.getCool()) {
                        eneTroop.attack(closestTroop);
                        animations.put(eneTroop, new Troop(eneTroop, eneTroop.getAnimation(), closestTroop.getLocation()));
                        eneTroop.setLastAttack(i);
                        if (closestTroop.isDead()) {
                            frendlys.remove(closestTroop);
                        }
                    }
                } else {
                    eneTroop.move();
                }
            }
            
            //spremeni globaln timer
            i++;
            //pogleda če lhka prišteje elixer in če lahko ga
            if (i % 40 == 0 && freElix < 10) {
                freElix++;
            }
            if (i % 40 == 0 && eneElix < 10) {
                eneElix++;
            }

            if (frendlys.size() == 0) {
                break;
            }
            if (enemys.size() == 0) {
                break;
            }
            playArea.repaint(); // ponoven izris okna
            elix.repaint();
            cards.repaint();
            try {
                Thread.sleep(50); // počakaj 50 ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }}
            frame.dispose();
            //odloči kdo zmaga glede na to kdo ma na koncu več življenja na towerjih
            int winConFre = 0;
            int winConEne = 0;
            for (Troop frendly: MainLoop.frendlys) {
                if (frendly.getName().equals("Tower")) {
                    winConFre = winConFre + frendly.getCurrenthealth();
                }
            }
            for (Troop enemy: MainLoop.enemys) {
                if (enemy.getName().equals("Tower")) {
                    winConEne = winConEne + enemy.getCurrenthealth();
                }
            }
            if (winConEne < winConFre) {
                situation = "Winner";
            }
            else if (winConEne == winConFre) {
                situation = "Draw";
            }
            else {
                situation = "Loser";
            }     
            frame = new JFrame();
            frame.setSize(new Dimension(WIDTH, HEIGHT));
            frame.setLayout(new GridLayout(1,1));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            WinPanel winner = new WinPanel();
            frame.add(winner);
            frame.setVisible(true);
            while (true) {
                winner.repaint();
                try {
                    Thread.sleep(50); // počakaj 50 ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }}

    
class CumPanel extends JPanel {
        public CumPanel() {
        super();
    }
        @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics = (Graphics2D)g; 
        int width = 450;
        int height = 95; 
        graphics.drawImage(MainLoop.bananaBack, 0,0, width, height*10+50,null);
        for (int i = 0; i < MainLoop.freElix; i++) {
            graphics.drawImage(MainLoop.BANANA, width/6, i * height, width-width/3, height, null);
        }  
        if (MainLoop.freElix == 10) {
            graphics.setColor(Color.RED);
            graphics.rotate(Math.PI/2);

            graphics.setFont(new Font(Font.MONOSPACED, Font.BOLD, 100));
            graphics.drawString("LEAKING BANANA", height/2, -10);
            graphics.drawString("LEAKING BANANA", height/2, -width+90);
            graphics.rotate(-Math.PI/2);
        }
        else {
        graphics.setColor(Color.YELLOW);
        graphics.setFont(new Font("Montserrat", Font.BOLD, 50));
        graphics.drawString(MainLoop.freElix+" BANANA", width/2-150, 950);
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
        graphics.drawImage(MainLoop.backround, 0,0, 1200,1200,null);
        graphics.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        graphics.setColor(Color.BLACK);
        graphics.drawString(String.format("%.0fs",  (3600-MainLoop.i)*0.05 ), 20, 20);
        int picSize = 80;
        graphics.setFont(new Font("Montserrat", Font.BOLD, 10));
        //transliram koordinaten sistem v koordinaten sistem centriran na monkeya nati izrišem sliko monkeya in prikaz trenutnega healtha
        for (Troop freTroop: MainLoop.frendlys) {
            graphics.translate(freTroop.getLocation().getX(), freTroop.getLocation().getY());
            graphics.rotate(freTroop.getOrientation());
            graphics.drawImage(freTroop.getPicture(), -picSize/2, -picSize/2, picSize, picSize, null);
            graphics.rotate(-freTroop.getOrientation());
            graphics.setColor(Color.GREEN);
            graphics.fillRect(-picSize/2, -picSize/2-10, freTroop.getCurrenthealth()*picSize/freTroop.getMaxhealth(), picSize/10);
            graphics.setColor(Color.BLACK);
            graphics.drawRect(-picSize/2, -picSize/2-10, picSize, picSize/10);
            graphics.drawString(freTroop.getCurrenthealth()+"/"+freTroop.getMaxhealth(), -picSize/4, -picSize/2-2);
            graphics.setTransform(base);
        }
        //isto sranje k zgori sam za enemye
        for (Troop eneTroop: MainLoop.enemys) {
            graphics.translate(eneTroop.getLocation().getX(), eneTroop.getLocation().getY());
            graphics.rotate(eneTroop.getOrientation());
            graphics.drawImage(eneTroop.getPicture(), -picSize/2, -picSize/2, picSize, picSize, null);
            graphics.rotate(-eneTroop.getOrientation());
            graphics.setColor(Color.RED);
            graphics.fillRect(-picSize/2, -picSize/2-10, eneTroop.getCurrenthealth()*picSize/eneTroop.getMaxhealth(), picSize/10);
            graphics.setColor(Color.BLACK);
            graphics.drawRect(-picSize/2, -picSize/2-10, picSize, picSize/10);
            graphics.drawString(eneTroop.getCurrenthealth()+"/"+eneTroop.getMaxhealth(), -picSize/4, -picSize/2-2);
            graphics.setTransform(base);
        }
        //narišem "animacije" sam za breif moment de zgleda k de je nek projectile letel in pol jih izbrišem iz seznama stvari za "animirat"
        MainLoop.animations.keySet().removeIf(animatroop -> (MainLoop.i-animatroop.getLastAttack() > 4));
        graphics.setFont(new Font("Montserrat", Font.BOLD, 30));
        graphics.setColor(Color.RED);
        for (Troop attackingTroop: MainLoop.animations.keySet()) {
            graphics.translate(MainLoop.animations.get(attackingTroop).getLocation().getX(), MainLoop.animations.get(attackingTroop).getLocation().getY());
            graphics.rotate(attackingTroop.getOrientation());
            graphics.drawImage(MainLoop.animations.get(attackingTroop).getPicture(), 0, -picSize/4, picSize/2, picSize/2, null);
            MainLoop.animations.get(attackingTroop).move();
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
        graphics.drawImage(MainLoop.bananaBack, 0,0, 450, 1000,null);
        graphics.setFont(new Font("Montserrat", Font.BOLD, 22));
        graphics.setStroke(new BasicStroke(2));
        int ind = MainLoop.troopSelection.indexOf(MainLoop.selectedName);
        //izdrišem karte monkeyu kjer so info in označim z rdečo obrobo kateri je trenutno izbran
        for (int i = 0; i < 4; i++) {
            if (ind == i) {
                graphics.setStroke(new BasicStroke(5));
                graphics.setColor(Color.RED);
                graphics.drawRect(0, i*250, 450, 250);

            }
            else {
                graphics.setColor(Color.YELLOW);
                graphics.setStroke(new BasicStroke(2));
                graphics.drawRect(0, i*250, 450, 250);
            }
            Troop troop = new Troop(new Vektor(0,0), true, MainLoop.troopSelection.get(i));
            graphics.drawImage(troop.getPicture(),0,i*250, 250, 250, null);
            graphics.drawString("cost: " + troop.getCost() + " BANANA", 250, i*250+22);
            graphics.drawString(troop.getName(), 0, i*250+20);
            graphics.drawString("health: " + troop.getMaxhealth() + "HP", 250, i*250+45);
            graphics.drawString("range: " + troop.getRange()/30, 250, i*250+67);
            graphics.drawString("damage: " + troop.getDamage()+ "HP", 250, i*250+89);
            graphics.drawString(String.format("cool: %.1f s", troop.getCool()*0.05 ), 250, i*250+111);
            graphics.drawString(String.format("speed: %.1f", troop.getSpeed() ), 250, i*250+133);
        }  
    }
    }   
    class WinPanel extends JPanel {
        public WinPanel() {
        super();
        if (MainLoop.situation.equals("Winner")) {
            setBackground(Color.YELLOW);
        }
        else if (MainLoop.situation.equals("Loser")) {
            setBackground(Color.RED);
        }
        else {
            setBackground(Color.BLACK);
        }
    }
        @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics = (Graphics2D)g; 
        graphics.setFont(new Font(Font.MONOSPACED, Font.BOLD, 100));
        
        if (MainLoop.situation.equals("Winner")) {
            graphics.setColor(Color.RED);
        }
        else if (MainLoop.situation.equals("Loser")) {
            graphics.setColor(Color.YELLOW);
        }
        else {
            graphics.setColor(Color.WHITE);
        }
        try {
        graphics.drawString(MainLoop.situation, (int) this.getMousePosition().getX(), (int) this.getMousePosition().getY());
        } catch(NullPointerException e) {}
    }
}


