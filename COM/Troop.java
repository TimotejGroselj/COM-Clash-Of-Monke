package COM;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.imageio.ImageIO;

public class Troop {

    private BufferedImage picture;
    private int speed;
    private int damage;
    private int range;
    private int cool;
    private int maxhealth;
    private int cost;
    private double orientation;
    private Vektor location;
    private String animation;
    private int currenthealth;
    private boolean isFrendly;
    private String name;
    private int lastAttack;
    

    public Troop(Vektor location, boolean isFrendly, String name)  {
        if (name.equals("Tower")) {
            try {
            this.picture = ImageIO.read(new File("pictures","tower.png"));
            } catch (IOException e) {
            e.printStackTrace();
            }
            this.animation = "towerrocket.png";
            this.orientation = Math.PI/2;
            this.location = location;
            this.isFrendly = isFrendly;
            this.lastAttack=-100;
            this.name = name;
            setSpeed(0);
            setDamage(11);
            setRange(8);
            setCool(3);
            setMaxhealth(30);
            setCurrenthealth(getMaxhealth());
            this.cost = 0;
        }

        //initiatana fixna Tower pa bridge k sta mal built diffrent

        //cajt za standard balancing: vsaka kategorija ima svoje št barov k vplivajo na ta določeno kategorijo (delam use od oka tuk de please forgive me in spreminjala bova 
        //kok vsak level doda vsazga stata)
        //speed 5 levelu: vsak level doda 1pixl na iteracijo
        //damage 10 levelu: vsak level doda 2 damage point
        //range 10 levelu: vsak level doda 30 pixlov ranga
        //cool 10 levelu: vsak level zbije cooldown iz 110 iteracij za 10 (iz 5s cooldowna na 0.5s cooldowna)
        //maxHealth 10 levelu: vsak level doda 10HP
        //cost 9 levelu: vsak level zbije od 10 en elixir

        //sum vseh pointov nj bo 25 pointov ki jih lahko porabiš kakor želiš dokler so v limiti.
        else if (name.equals("Bomerang")) {

            this.isFrendly = isFrendly;
            this.name = name;
            try {
            this.picture = ImageIO.read(new File("pictures","bommerang.png"));
            } catch (IOException e) {
            e.printStackTrace();
            }
            this.animation = "bommer.png";
            setOrientation(Math.PI/2);
            setLastAttack(-100);
            setLocation(location);
            setSpeed(2);
            setDamage(5);
            setRange(5);
            setCool(5);
            setMaxhealth(5);
            setCurrenthealth(getMaxhealth());
            setCost(5);
        }
        else if (name.equals("Monke")) {
            this.isFrendly = isFrendly;
            this.name = name;
            try {
            this.picture = ImageIO.read(new File("pictures","basicMonke.png"));
            } catch (IOException e) {
            e.printStackTrace();
            }

            this.animation = "dart.png";

            setOrientation(Math.PI/2);
            setLastAttack(-100);
            setLocation(location);
            setSpeed(5);
            setDamage(5);
            setRange(5);
            setCool(5);
            setMaxhealth(0.5);
            setCurrenthealth(getMaxhealth());
            setCost(9);
        }

            else if (name.equals("Ice wizard")) {

            this.isFrendly = isFrendly;
            this.name = name;
            try {
            this.picture = ImageIO.read(new File("pictures","wizi.png"));
            } catch (IOException e) {
            e.printStackTrace();
            }
            this.animation = "frezebolt.png";
   
            setOrientation(Math.PI/2);
            setLastAttack(-100);
            setLocation(location);
            setSpeed(4);
            setDamage(4);
            setRange(4);
            setCool(5);
            setMaxhealth(5);
            setCurrenthealth(getMaxhealth());
            setCost(6);
        }

        else if (name.equals("Super")) {

            this.isFrendly = isFrendly;
            this.name = name;
            try {
            this.picture = ImageIO.read(new File("pictures","super.png"));
            } catch (IOException e) {
            e.printStackTrace();
            }

            this.animation = "dart.png";
  
            setOrientation(Math.PI/2);
            setLastAttack(-100);
            setLocation(location);
            setSpeed(2);
            setDamage(1);
            setRange(5);
            setCool(10.5);
            setMaxhealth(7);
            setCurrenthealth(getMaxhealth());
            setCost(2);
        }
            else if (name.equals("Mortar")) {

            this.isFrendly = isFrendly;
            this.name = name;
            try {
            this.picture = ImageIO.read(new File("pictures","mortar.png"));
            } catch (IOException e) {
            e.printStackTrace();
            }
            this.animation = "bomb.png";
     
            setOrientation(Math.PI/2);
            setLastAttack(-100);
            setLocation(location);
            setSpeed(0);
            setDamage(9);
            setRange(15);
            setCool(1);
            setMaxhealth(7);
            setCurrenthealth(getMaxhealth());
            setCost(4);
        }

            else if (name.equals("Fire wizard")) {
            this.isFrendly = isFrendly;
            this.name = name;
            try {
            this.picture = ImageIO.read(new File("pictures","FireWizard.png"));
            } catch (IOException e) {
            e.printStackTrace();
            }
            this.animation = "laserblast.png";
            setOrientation(Math.PI/2);
            setLastAttack(-100);
            setLocation(location);
            setSpeed(4);
            setDamage(5);
            setRange(7);
            setCool(3);
            setMaxhealth(2);
            setCurrenthealth(getMaxhealth());
            setCost(5);
        }
            else if (name.equals("CHIPPER")) {
            this.isFrendly = isFrendly;
            this.name = name;
            try {
            this.picture = ImageIO.read(new File("pictures","Chipper.png"));
            } catch (IOException e) {
            e.printStackTrace();
            }
            this.animation = "sparkles.png";
            setOrientation(Math.PI/2);
            setLastAttack(-100);
            setLocation(location);
            setSpeed(1);
            setDamage(0.5);
            setRange(2);
            setCool(11); //jap nima cooldowna vsako kurcevi iteracijo dela damage
            setMaxhealth(8);
            setCurrenthealth(getMaxhealth());
            setCost(1);
        }
        else if (name.equals("Acid thrower")) {
        this.isFrendly = isFrendly;
        this.name = name;
        try {
        this.picture = ImageIO.read(new File("pictures","acidthrower.png"));
        } catch (IOException e) {
        e.printStackTrace();
        }
        this.animation = "acid.png";
        setOrientation(Math.PI/2);
        setLastAttack(-100);
        setLocation(location);
        setSpeed(3);
        setDamage(0.1);
        setRange(4);
        setCool(11); 
        setMaxhealth(3);
        setCurrenthealth(getMaxhealth());
        setCost(6);
        }
        else if (name.equals("Flame thrower")) {
        this.isFrendly = isFrendly;
        this.name = name;
        try {
        this.picture = ImageIO.read(new File("pictures","flamethrower.png"));
        } catch (IOException e) {
        e.printStackTrace();
        }
        this.animation = "firebolt.png";
        setOrientation(Math.PI/2);
        setLastAttack(-100);
        setLocation(location);
        setSpeed(3);
        setDamage(0.5);
        setRange(4);
        setCool(10.8);
        setMaxhealth(5);
        setCurrenthealth(getMaxhealth());
        setCost(4);
        }
        else if (name.equals("Alien")) {
        this.isFrendly = isFrendly;
        this.name = name;
        try {
        this.picture = ImageIO.read(new File("pictures","alien.png"));
        } catch (IOException e) {
        e.printStackTrace();
        }
        this.animation = "alienshot.png";
        setOrientation(Math.PI/2);
        setLastAttack(-100);
        setLocation(location);
        setSpeed(3);
        setDamage(10);
        setRange(7);
        setCool(0); 
        setMaxhealth(1);
        setCurrenthealth(getMaxhealth());
        setCost(6);
        }
        else if (name.equals("Wizard")) {
        this.isFrendly = isFrendly;
        this.name = name;
        try {
        this.picture = ImageIO.read(new File("pictures","basicwizi.png"));
        } catch (IOException e) {
        e.printStackTrace();
        }
        this.animation = "wizi.png";
        setOrientation(Math.PI/2);
        setLastAttack(-100);
        setLocation(location);
        setSpeed(5);
        setDamage(3);
        setRange(4);
        setCool(5); 
        setMaxhealth(4);
        setCurrenthealth(getMaxhealth());
        setCost(7);
        }
        else if (name.equals("Bionic bommer")) {
        this.isFrendly = isFrendly;
        this.name = name;
        try {
        this.picture = ImageIO.read(new File("pictures","bionicbommer.png"));
        } catch (IOException e) {
        e.printStackTrace();
        }
        this.animation = "basicbomerang.png";
        setOrientation(Math.PI/2);
        setLastAttack(-100);
        setLocation(location);
        setSpeed(3);
        setDamage(1);
        setRange(4);
        setCool(10); 
        setMaxhealth(4);
        setCurrenthealth(getMaxhealth());
        setCost(7);
        }
        else if (name.equals("Bommerang master")) {
        this.isFrendly = isFrendly;
        this.name = name;
        try {
        this.picture = ImageIO.read(new File("pictures","bommerninja.png"));
        } catch (IOException e) {
        e.printStackTrace();
        }
        this.animation = "glaive.png";
        setOrientation(Math.PI/2);
        setLastAttack(-100);
        setLocation(location);
        setSpeed(4);
        setDamage(5);
        setRange(5);
        setCool(3); 
        setMaxhealth(8);
        setCurrenthealth(getMaxhealth());
        setCost(5);
        }
        else if (name.equals("Canon")) {
        this.isFrendly = isFrendly;
        this.name = name;
        try {
        this.picture = ImageIO.read(new File("pictures","canon.png"));
        } catch (IOException e) {
        e.printStackTrace();
        }
        this.animation = "bomb.png";
        setOrientation(Math.PI/2);
        setLastAttack(-100);
        setLocation(location);
        setSpeed(0);
        setDamage(3);
        setRange(6);
        setCool(7); 
        setMaxhealth(5);
        setCurrenthealth(getMaxhealth());
        setCost(7);
        }
        else if (name.equals("Catapult")) {
        this.isFrendly = isFrendly;
        this.name = name;
        try {
        this.picture = ImageIO.read(new File("pictures","catapult.png"));
        } catch (IOException e) {
        e.printStackTrace();
        }
        this.animation = "catapult.png";
        setOrientation(Math.PI/2);
        setLastAttack(-100);
        setLocation(location);
        setSpeed(1);
        setDamage(5);
        setRange(9);
        setCool(1); 
        setMaxhealth(5);
        setCurrenthealth(getMaxhealth());
        setCost(7);
        }
        else if (name.equals("Engineer")) {
        this.isFrendly = isFrendly;
        this.name = name;
        try {
        this.picture = ImageIO.read(new File("pictures","enginer.png"));
        } catch (IOException e) {
        e.printStackTrace();
        }
        this.animation = "tack.png";
        setOrientation(Math.PI/2);
        setLastAttack(-100);
        setLocation(location);
        setSpeed(3);
        setDamage(3);
        setRange(3);
        setCool(6); 
        setMaxhealth(8);
        setCurrenthealth(getMaxhealth());
        setCost(7);
        }
        else if (name.equals("Freze tower")) {
        this.isFrendly = isFrendly;
        this.name = name;
        try {
        this.picture = ImageIO.read(new File("pictures","frezze.png"));
        } catch (IOException e) {
        e.printStackTrace();
        }
        this.animation = "sparkles.png";
        setOrientation(Math.PI/2);
        setLastAttack(-100);
        setLocation(location);
        setSpeed(2);
        setDamage(2);
        setRange(2);
        setCool(4); 
        setMaxhealth(11);
        setCurrenthealth(getMaxhealth());
        setCost(6);
        }
        else if (name.equals("Jaka")) {
        this.isFrendly = isFrendly;
        this.name = name;
        try {
        this.picture = ImageIO.read(new File("pictures","jaka.png"));
        } catch (IOException e) {
        e.printStackTrace();
        }
        this.animation = "boom.png";
        setOrientation(Math.PI/2);
        setLastAttack(-40000);
        setLocation(location);
        setSpeed(11);
        setDamage(75);
        setRange(2);
        this.cool = 3600;
        setMaxhealth(1);
        setCurrenthealth(getMaxhealth());
        setCost(8);
        }
        else if (name.equals("Laser gunner")) {
        this.isFrendly = isFrendly;
        this.name = name;
        try {
        this.picture = ImageIO.read(new File("pictures","laserminigun.png"));
        } catch (IOException e) {
        e.printStackTrace();
        }
        this.animation = "lasershot.png";
        setOrientation(Math.PI/2);
        setLastAttack(-100);
        setLocation(location);
        setSpeed(3);
        setDamage(2.5);
        setRange(6);
        setCool(9.5); 
        setMaxhealth(3);
        setCurrenthealth(getMaxhealth());
        setCost(3);
        }
        else if (name.equals("Minigun")) {
        this.isFrendly = isFrendly;
        this.name = name;
        try {
        this.picture = ImageIO.read(new File("pictures","minigun.png"));
        } catch (IOException e) {
        e.printStackTrace();
        }
        this.animation = "dart.png";
        setOrientation(Math.PI/2);
        setLastAttack(-100);
        setLocation(location);
        setSpeed(3);
        setDamage(1);
        setRange(5);
        setCool(11); 
        setMaxhealth(3);
        setCurrenthealth(getMaxhealth());
        setCost(2);
        }
        else if (name.equals("Ninja")) {
        this.isFrendly = isFrendly;
        this.name = name;
        try {
        this.picture = ImageIO.read(new File("pictures","ninja.png"));
        } catch (IOException e) {
        e.printStackTrace();
        }
        this.animation = "shuriken.png";
        setOrientation(Math.PI/2);
        setLastAttack(-100);
        setLocation(location);
        setSpeed(5);
        setDamage(3);
        setRange(4);
        setCool(6); 
        setMaxhealth(3);
        setCurrenthealth(getMaxhealth());
        setCost(7);
        }
        else if (name.equals("Plasma super")) {
        this.isFrendly = isFrendly;
        this.name = name;
        try {
        this.picture = ImageIO.read(new File("pictures","plasmasuper.png"));
        } catch (IOException e) {
        e.printStackTrace();
        }
        this.animation = "plasma.png";
        setOrientation(Math.PI/2);
        setLastAttack(-100);
        setLocation(location);
        setSpeed(2);
        setDamage(2);
        setRange(4);
        setCool(8);
        setMaxhealth(10);
        setCurrenthealth(getMaxhealth());
        setCost(5);
        }
        else if (name.equals("Sniper")) {
        this.isFrendly = isFrendly;
        this.name = name;
        try {
        this.picture = ImageIO.read(new File("pictures","sniper.png"));
        } catch (IOException e) {
        e.printStackTrace();
        }
        this.animation = "cross.png";
        setOrientation(Math.PI/2);
        setLastAttack(-100);
        setLocation(location);
        setSpeed(3);
        setDamage(7);
        setRange(12);
        setCool(2);
        setMaxhealth(2);
        setCurrenthealth(getMaxhealth());
        setCost(5);
        }        
        else if (name.equals("Timo")) {
        this.isFrendly = isFrendly;
        this.name = name;
        try {
        this.picture = ImageIO.read(new File("pictures","timotej.png"));
        } catch (IOException e) {
        e.printStackTrace();
        }
        this.animation = "tack.png";
        setOrientation(Math.PI/2);
        setLastAttack(-100);
        setLocation(location);
        setSpeed(4);
        setDamage(4);
        setRange(6);
        setCool(5);
        setMaxhealth(4);
        setCurrenthealth(getMaxhealth());
        setCost(8);
        }
    }
    public Troop(Troop freTroop, String name, Vektor enelocation) {
        //to pomen de smo v animaciji
        //to pomen da noben ime ni Troop ampk bo animacija in je važn sam speed pa orientacije in slikca
            this.speed = (int) Vektor.dist(freTroop.getLocation(), enelocation)/4;
            try {
            this.picture = ImageIO.read(new File("animations", name));
            } catch (IOException e) {
            e.printStackTrace();
            } 
            setLocation(freTroop.getLocation());
            setOrientation(freTroop.getOrientation());
    }

    //te metode so samo za kvazi balancanje stvari
    private void setSpeed(double speed) {
        this.speed = (int) speed;
    }
    private void setDamage(double damage) {
        this.damage = (int) (20*damage);
    }
    private void setRange(double range) {
        this.range = (int) (30*range);
    }
    private void setCool(double cool) {
        this.cool = (int) (110-10*cool);
    }
    private void setMaxhealth(double maxhealth) {
        this.maxhealth = (int) (100*maxhealth);
    }
    private void setCost(int cost) {
        this.cost = 10-cost;
    }
    // do sm

    public double getOrientation() {
        return orientation;
    }
    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }
    public int getCurrenthealth() {
        return currenthealth;
    }
    public void setCurrenthealth(int currenthealth) {
        this.currenthealth = currenthealth;
    }
    public Vektor getLocation() {
        return location;
    }
    public void setLocation(Vektor location) {
        this.location = location;
    }
    public BufferedImage getPicture() {
        return picture;
    }
    public double getSpeed() {
        return speed;
    }
    public int getDamage() {
        return damage;
    }
    public int getRange() {
        return range;
    }
    public double getCool() {
        return cool;
    }
    public int getMaxhealth() {
        return maxhealth;
    }
    public int getCost() {
        return cost;
    }
    public String getAnimation() {
        return animation;
    }
    public String getName() {
        return name;
    }
    public int getLastAttack() {
        return lastAttack;
    }
    public void setLastAttack(int lastAttack) {
        this.lastAttack = lastAttack;
    }
    public boolean isFrendly() {
        return isFrendly;
    }

    public boolean isDead() {
        if (this.getCurrenthealth()<=0) {
            return true;
        }
        return false;
    }

    public void move() {
        //nastavi lokacijo troopa po premiku, ki ga opravi v timestepu
        Vektor move = new Vektor(Math.cos(this.getOrientation()) * this.getSpeed(), Math.sin(this.getOrientation()) * this.getSpeed());
        this.setLocation(Vektor.plus(move, this.getLocation()));
    }
    public void attack(Troop victim) {
        //das mu victim in nrdi attack
        //sproz animacijo somehow?
        victim.setCurrenthealth(victim.getCurrenthealth()-this.getDamage());
    }
    public boolean isInRange(Troop victim) {
        return (Vektor.dist(this.getLocation(), victim.getLocation())<=this.getRange());
    }
    public boolean isOnFrendlyGround(int height) {
        if (this.getLocation().getY() > height/2) {
            return this.isFrendly();
        }
        return !this.isFrendly();

    }
    public Troop pathFind(Set <Troop> enemys) {
        //fuck it we ball
        double min = 100000.0; 
        Troop pathPoinTroop = null;
        for (Troop enemy: enemys) {
            double dist = Vektor.dist(enemy.getLocation(),this.getLocation());
            if (dist < min) {
                min = dist;
                pathPoinTroop = enemy;
            }
        }
        this.setOrientation(Vektor.angle(this.getLocation(), pathPoinTroop.getLocation()));
        return pathPoinTroop;
    }
}
