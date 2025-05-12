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
public void pathFind(Grid grid) {
	//nastav oreintacijo tko, da ko se bo premiku bo pršu v najbižji objective aka bridge al pa enemy tower
	if (grid.isOnFrendlyGround(this.getLocation())) {
			if ((Vektor.dist(grid.getBridgeLoc()[0],this.getLocation())) < (Vektor.dist(grid.getBridgeLoc()[1],this.getLocation()))) {
				this.setOrientation(Vektor.angle(this.getLocation(), grid.getBridgeLoc()[1]));
			}
			else {
				this.setOrientation(Vektor.angle(this.getLocation(), grid.getBridgeLoc()[0]));
			}
	}
	else {
		if ((Vektor.dist(grid.getEneTowerLoc()[0],this.getLocation())) < (Vektor.dist(grid.getEneTowerLoc()[1],this.getLocation()))) {
			this.setOrientation(Vektor.angle(this.getLocation(), grid.getEneTowerLoc()[1]));
		}
		else {
			this.setOrientation(Vektor.angle(this.getLocation(), grid.getEneTowerLoc()[0]));
		}
	}
}



}
