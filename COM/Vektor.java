package COM;

public class Vektor {
    private double x;
    private double y;
    public Vektor(double x, double y) {
        super();
        this.x = x;
        this.y = y;
    }
    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }

    public static Vektor plus(Vektor v1, Vektor v2) {
        return new Vektor(v1.getX()+v2.getX(),v1.getY()+v2.getY());
    }

    public static double dist(Vektor v1,Vektor v2) {
        return Math.sqrt(Math.pow(v1.getX()-v2.getX(), 2) + Math.pow(v1.getY()-v2.getY(), 2));
    }

    public static double angle(Vektor v1,Vektor v2) {
        //should worky?
        double dx = v2.getX() - v1.getX();
        double dy = v2.getY() - v1.getY();
        double angle = Math.atan(dy/dx);
        if (dx < 0 && dy > 0 || dx < 0 && dy < 0) {
            return Math.PI+angle;
        }
        else if (dx > 0 && dy < 0) {
            return 2*Math.PI+angle;
        }
        else {
            return angle;
        }
    }
    public static void main(String[] args){
        Vektor vek = new Vektor(2.0,2.0);
        System.out.println(vek.getX());

    }
}
