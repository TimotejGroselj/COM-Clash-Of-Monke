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
    private int[][] animation = new int[2][2];
    private int currenthealth;
    private boolean isFrendly;
    private String name;
    private int lastAttack;

    public Troop(Vektor location, boolean isFrendly, String name)  {
        if (name.equals("Tower")) {
            try {
            this.picture = ImageIO.read(new File("tower.png"));
            } catch (IOException e) {
            e.printStackTrace();
            }
            this.orientation = Math.PI/2;
            this.location = location;
            this.isFrendly = isFrendly;
            this.lastAttack=-100;
            this.name = name;
            this.speed = 0;
            setDamage(8);
            setRange(8);
            setCool(3);
            this.maxhealth = 500;
            setCurrenthealth(getMaxhealth());
            this.cost = 0;
        }
        if (name.equals("Bridge")) {
           try {
            this.picture = ImageIO.read(new File("TeseterMonke.png"));
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
            setCurrenthealth(getMaxhealth());
            this.cost = 0;
        }



        //initiatana fixna Tower pa bridge k sta mal built diffrent

        //cajt za standard balancing: vsaka kategorija ima 9 barov k vplivajo na ta določeno kategorijo (delam use od oka tuk de please forgive me in spreminjala bova 
        //kok vsak level doda vsazga stata)
        //speed: vsak level doda 1pixla na iteracijo
        //damage: vsak level doda 3 damage point
        //range: vsak level doda 5 pixlov ranga
        //cool: vsak level zbije cooldown iz 80 iteracij za 8 (iz 4s cooldowna na 0.4s cooldowna)
        //maxHealth: vsak level doda 10HP
        //cost: vsak level zbije od 10 en elixir

        //balancan troop recva de je najbl middle of the road ass troop k ma use 5 aka premika se z 10 pixli na iteracijo (glede na max window aka 1920x1080)
        //ma 5 damage ma 150 pixlov ranga ma cooldown 40 iteracij (delam pod assumptionom de bo repain 50ms) ma 40 maxHealth in stane 5 elixirja.

        //sum vseh pointov je pol u tm primeru 30 pointov ki jih lahko porabiš kakor želiš.
        if (name.equals("TesterMonke")) {
           try {
            this.picture = ImageIO.read(new File("TeseterMonke.png"));
            } catch (IOException e) {
            e.printStackTrace();
            }
            this.orientation = Math.PI/2;
            this.lastAttack=-100;
            this.location = location;
            this.isFrendly = isFrendly;
            this.name = name;
            setSpeed(5);
            setDamage(5);
            setRange(5);
            setCool(5);
            setMaxhealth(5);
            setCurrenthealth(getMaxhealth());
            setCost(5);
        }
        if (name.equals("basic monke")) {
           try {
            this.picture = ImageIO.read(new File("basicMonke.png"));
            } catch (IOException e) {
            e.printStackTrace();
            }
            this.orientation = Math.PI/2;
            this.lastAttack=-100;
            this.location = location;
            this.isFrendly = isFrendly;
            this.name = name;
            setSpeed(9);
            setDamage(2);
            setRange(2);
            setCool(7);
            setMaxhealth(1);
            setCurrenthealth(getMaxhealth());
            setCost(9);
        }
    }
    

    //tuki prefukava konstante de balancava shit
    private void setSpeed(int speed) {
        this.speed = speed;
    }
    private void setDamage(int damage) {
        this.damage = 3*damage;
    }
    private void setRange(int range) {
        this.range = 5*range;
    }
    private void setCool(int cool) {
        this.cool = 80-8*cool;
    }
    private void setMaxhealth(int maxhealth) {
        this.maxhealth = 8*maxhealth;
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
    public int[][] getAnimation() {
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
