import java.awt.image.BufferedImage;

public class Tower {
    //basicly troop sam de se ne premika lol
    private BufferedImage picture;
    private int damage;
    private int range;
    private double cool;
    private int maxhealth;
    private double orientation;
    private Vektor location;
    private int[][] animation = new int[2][2];
    private int currenthealth;
    public Tower(double orientation, Vektor location) {
        super();
        this.picture = null;
        this.damage = 5;
        this.range = 5;
        this.cool = 0.5;
        this.maxhealth = 30;
        this.orientation = orientation;
        this.location = location;
        this.animation = null;
        this.currenthealth = this.maxhealth;
    }
    public BufferedImage getPicture() {
        return picture;
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
    public double getOrientation() {
        return orientation;
    }
    public Vektor getLocation() {
        return location;
    }
    public int[][] getAnimation() {
        return animation;
    }
    public int getCurrenthealth() {
        return currenthealth;
    }
    public void setCurrenthealth(int currenthealth) {
        this.currenthealth = currenthealth;
    }
    public boolean isDead() {
        if (this.getCurrenthealth()<=0) {
            return true;
        }
        return false;
    }
    public void attack(Troop victim) {
        //sproz animacijo somehow?
        victim.setCurrenthealth(victim.getCurrenthealth()-this.getDamage());
    }
    public boolean isInRange(Troop victim) {
        return (Vektor.dist(this.getLocation(), victim.getLocation())<=this.getRange());
    }

}
