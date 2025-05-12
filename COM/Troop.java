package COM;

import java.awt.image.BufferedImage;

public class Troop {

    private BufferedImage picture;
    private double speed;
    private int damage;
    private int range;
    private double cool;
    private int maxhealth;
    private int cost;
    private double orientation;
    private Vektor location;
    private int[][] animation = new int[2][2];
    private int currenthealth;
    public Troop(BufferedImage picture, double speed, int damage, int range, double cool, int maxhealth, int cost,
                 double orientation, Vektor location, int[][] animation, int currenthealth) {

        this.picture = picture;
        this.speed = speed;
        this.damage = damage;
        this.range = range;
        this.cool = cool;
        this.maxhealth = maxhealth;
        this.cost = cost;
        this.orientation = orientation;
        this.location = location;
        this.animation = animation;
        this.currenthealth = currenthealth;
    }
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

    public void setPicture(BufferedImage picture) {
        this.picture = picture;
    }
    public boolean isDead() {
        if (this.getCurrenthealth()<=0) {
            return true;
        }
        return false;
    }
    public void move(double timestep) {
        //nastavi lokacijo troopa po premiku, ki ga opravi v timestepu
        double normSpeed = timestep * this.getSpeed();
        Vektor move = new Vektor(Math.cos(this.getOrientation()) * normSpeed, Math.sin(this.getOrientation()) * normSpeed);
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
    public void pathFind(Grid grid, boolean isFrendly) {
        //fuck it we ball
        double min = 300.0; 
        Troop pathPoinTroop = null;
        for (Troop enemy: grid.getTroops(isFrendly)) {
            if ((!grid.isOnFrendlyGround(this.getLocation(),isFrendly)) && enemy instanceof Bridge) {
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
