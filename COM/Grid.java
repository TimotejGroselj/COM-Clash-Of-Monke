package COM;

public class Grid {
    private int xSize;
    private int ySize;
    private Tower[] freTower= new Tower[2];
    //frendly towerja
    private Tower[] eneTower = new Tower[2];
    //enemy towerja
    private Vektor[] bridgeLoc= new Vektor[2];
    public Grid() {
        super();
        this.xSize = 100;
        this.ySize = 200;
        this.freTower = new Tower[] {new Tower(Math.PI/2,new Vektor(10,10)),new Tower(Math.PI/2, new Vektor(90,10))};
        this.eneTower = new Tower[] {new Tower(Math.PI/2,new Vektor(10,90)),new Tower(Math.PI/2, new Vektor(90,90))};
        this.bridgeLoc = new Vektor[] {new Vektor(10,50), new Vektor(90, 50)};
    }
    public int getxSize() {
        return xSize;
    }
    public int getySize() {
        return ySize;
    }
    public Tower[] getFreTower() {
        return freTower;
    }
    public Tower[] getEneTower() {
        return eneTower;
    }
    public Vektor[] getFreTowerLoc() {
        Vektor[] loc = new Vektor[2];
        for (int i=0; i<2; i++) {
            loc[i] = this.getFreTower()[i].getLocation();
        }
        return loc;
    }
    public Vektor[] getEneTowerLoc() {
        Vektor[] loc = new Vektor[2];
        for (int i=0; i<2; i++) {
            loc[i] = this.getEneTower()[i].getLocation();
        }
        return loc;
    }
    public Vektor[] getBridgeLoc() {
        return bridgeLoc;
    }
    public boolean isOnFrendlyGround(Vektor lokacija) {
        if (lokacija.getY()<this.getySize()/2) {
            return true;
        }
        return false;
    }
}