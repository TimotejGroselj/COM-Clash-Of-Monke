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
    private BufferedImage animation;
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
            try {
            this.animation = ImageIO.read(new File("animations","towerrocket.png"));
            } catch (IOException e) {
            e.printStackTrace();
            }
            this.orientation = Math.PI/2;
            this.location = location;
            this.isFrendly = isFrendly;
            this.lastAttack=-100;
            this.name = name;
            this.speed = 0;
            setDamage(11);
            setRange(8);
            setCool(3);
            this.maxhealth = 200;
            setCurrenthealth(getMaxhealth());
            this.cost = 0;
        }
        if (name.equals("Bridge")) {
           try {
            this.picture = ImageIO.read(new File("pictures","TeseterMonke.png"));
            } catch (IOException e) {
            e.printStackTrace();
            }
            this.orientation = Math.PI/2;
            this.location = location;
            this.isFrendly = isFrendly;
            this.lastAttack=-100;
            this.name = name;
            this.speed = 0;
            this.damage = 0;
            this.range = 0;
            this.cool = 0;
            this.maxhealth = 0;
            this.currenthealth = this.maxhealth;
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
        if (name.equals("Bomerang")) {

            this.isFrendly = isFrendly;
            this.name = name;
            try {
            this.picture = ImageIO.read(new File("pictures","bommerang.png"));
            } catch (IOException e) {
            e.printStackTrace();
            }
            try {
            this.animation = (ImageIO.read(new File("animations", "bommer.png")));
            } catch (IOException e) {
            e.printStackTrace();
            }
            setOrientation(Math.PI/2);
            setLastAttack(-100);
            setLocation(location);
            setSpeed(2);
            setDamage(5);
            setRange(5);
            setCool(4);
            setMaxhealth(5);
            setCurrenthealth(getMaxhealth());
            setCost(4);
        }
        if (name.equals("Monke")) {
            this.isFrendly = isFrendly;
            this.name = name;
            try {
            setPicture(ImageIO.read(new File("pictures","basicMonke.png")));
            } catch (IOException e) {
            e.printStackTrace();
            }
            try {
            this.animation = (ImageIO.read(new File("animations", "dart.png")));
            } catch (IOException e) {
            e.printStackTrace();
            }
            setOrientation(Math.PI/2);
            setLastAttack(-100);
            setLocation(location);
            setSpeed(5);
            setDamage(5);
            setRange(5);
            setCool(5);
            setMaxhealth(2);
            setCurrenthealth(getMaxhealth());
            setCost(9);
        }

            if (name.equals("Ice wizard")) {

            this.isFrendly = isFrendly;
            this.name = name;
            try {
            this.picture = ImageIO.read(new File("pictures","wizi.png"));
            } catch (IOException e) {
            e.printStackTrace();
            }
            try {
            this.animation = (ImageIO.read(new File("animations", "frezebolt.png")));
            } catch (IOException e) {
            e.printStackTrace();
            }
            setOrientation(Math.PI/2);
            setLastAttack(-100);
            setLocation(location);
            setSpeed(4);
            setDamage(3);
            setRange(4);
            setCool(5);
            setMaxhealth(5);
            setCurrenthealth(getMaxhealth());
            setCost(5);
        }

        if (name.equals("Super")) {

            this.isFrendly = isFrendly;
            this.name = name;
            try {
            this.picture = ImageIO.read(new File("pictures","super.png"));
            } catch (IOException e) {
            e.printStackTrace();
            }
            try {
            this.animation = (ImageIO.read(new File("animations", "dart.png")));
            } catch (IOException e) {
            e.printStackTrace();
            }
            setOrientation(Math.PI/2);
            setLastAttack(-100);
            setLocation(location);
            setSpeed(2);
            setDamage(1);
            setRange(5);
            setCool(10);
            setMaxhealth(7);
            setCurrenthealth(getMaxhealth());
            setCost(2);
        }
            if (name.equals("Mortar")) {

            this.isFrendly = isFrendly;
            this.name = name;
            try {
            this.picture = ImageIO.read(new File("pictures","mortar.png"));
            } catch (IOException e) {
            e.printStackTrace();
            }
            try {
            this.animation = (ImageIO.read(new File("animations", "bomb.png")));
            } catch (IOException e) {
            e.printStackTrace();
            }
            setOrientation(Math.PI/2);
            setLastAttack(-100);
            setLocation(location);
            setSpeed(0);
            setDamage(10);
            setRange(17);
            setCool(1);
            setMaxhealth(7);
            setCurrenthealth(getMaxhealth());
            setCost(5);
        }

            if (name.equals("Fire wizard")) {

            this.isFrendly = isFrendly;
            this.name = name;
            try {
            this.picture = ImageIO.read(new File("pictures","FireWizard.png"));
            } catch (IOException e) {
            e.printStackTrace();
            }
            try {
            this.animation = (ImageIO.read(new File("animations", "laserblast.png")));
            } catch (IOException e) {
            e.printStackTrace();
            }
            setOrientation(Math.PI/2);
            setLastAttack(-100);
            setLocation(location);
            setSpeed(4);
            setDamage(7);
            setRange(7);
            setCool(3);
            setMaxhealth(2);
            setCurrenthealth(getMaxhealth());
            setCost(3);
        }

            if (name.equals("CHIPPER")) {

            this.isFrendly = isFrendly;
            this.name = name;
            try {
            this.picture = ImageIO.read(new File("pictures","Chipper.png"));
            } catch (IOException e) {
            e.printStackTrace();
            }
            try {
            this.animation = (ImageIO.read(new File("animations", "sparkles.png")));
            } catch (IOException e) {
            e.printStackTrace();
            }
            setOrientation(Math.PI/2);
            setLastAttack(-100);
            setLocation(location);
            setSpeed(1);
            this.damage = 1;
            setRange(2);
            setCool(11); //jap nima cooldowna vsako kurcevi iteracijo dela damage
            setMaxhealth(8);
            setCurrenthealth(getMaxhealth());
            setCost(1);
        }
    }
    

    //tuki prefukava konstante de balancava shit
    private void setSpeed(int speed) {
        this.speed = speed;
    }
    private void setDamage(int damage) {
        this.damage = 2*damage;
    }
    private void setRange(int range) {
        this.range = 30*range;
    }
    private void setCool(int cool) {
        this.cool = 110-10*cool;
    }
    private void setMaxhealth(int maxhealth) {
        this.maxhealth = 10*maxhealth;
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
    public BufferedImage getAnimation() {
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
    public void setPicture(BufferedImage picture) {
        this.picture = picture;
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
    public void pathFind(Set <Troop> enemys, int height) {
        //fuck it we ball
        double min = 100000.0; 
        Troop pathPoinTroop = null;
        for (Troop enemy: enemys) {
            if ((!this.isOnFrendlyGround(height)) && enemy.getName().equals("Bridge")) {
                continue;
            }
            double dist = Vektor.dist(enemy.getLocation(),this.getLocation());
            if (dist < min) {
                min = dist;
                pathPoinTroop = enemy;
            }
        }
        this.setOrientation(Vektor.angle(this.getLocation(), pathPoinTroop.getLocation()));
    }
}
